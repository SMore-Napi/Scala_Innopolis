package io

import scala.util.{Failure, Success, Try}

/** Inspired by https://github.com/typelevel/cats-effect/blob/series/3.x/core/shared/src/main/scala/cats/effect/IO.scala
  */
sealed trait IO[+A] {

  def map[B](f: A => B): IO[B] = IO.Map(this, f)

  def flatMap[B](f: A => IO[B]): IO[B] = IO.FlatMap(this, f)

  def *>[B](another: IO[B]): IO[B] = flatMap(_ => another)

  def as[B](newValue: => B): IO[B] = map(_ => newValue)

  def void: IO[Unit] = map(_ => ())

  def attempt: IO[Either[Throwable, A]] = IO(Try(unsafeRunSync()).map(Right(_)).recover { case e => Left(e) }.get)

  def option: IO[Option[A]] = redeem(_ => None, Some(_))

  def handleErrorWith[AA >: A](f: Throwable => IO[AA]): IO[AA] = IO(
    this match {
      case IO.ErrorModel(t) => f(t).unsafeRunSync()
      case _                => unsafeRunSync()
    }
  )

  def redeem[B](recover: Throwable => B, map: A => B): IO[B] = attempt.map(_.fold(recover, map))

  def redeemWith[B](recover: Throwable => IO[B], bind: A => IO[B]): IO[B] = attempt.flatMap(_.fold(recover, bind))

  def unsafeRunSync(): A = this match {
    case IO.Pure(a)                  => a
    case IO.Delay(body)              => body()
    case IO.Suspend(thunk)           => thunk().unsafeRunSync()
    case IO.Map(io, f)               => f(io.unsafeRunSync())
    case IO.FlatMap(io, f)           => f(io.unsafeRunSync()).unsafeRunSync()
    case IO.ErrorModel(t: Throwable) => throw t
  }
}

object IO {

  def apply[A](body: => A): IO[A] = delay(body)

  def suspend[A](thunk: => IO[A]): IO[A] = Suspend(() => thunk)

  def delay[A](body: => A): IO[A] = Delay(() => body)

  def pure[A](a: A): IO[A] = Pure(a)

  def fromEither[A](e: Either[Throwable, A]): IO[A] = e match {
    case Left(error)  => raiseError(error)
    case Right(value) => pure(value)
  }

  def fromOption[A](option: Option[A])(orElse: => Throwable): IO[A] = option match {
    case Some(value) => pure(value)
    case None        => raiseError(orElse)
  }

  def fromTry[A](t: Try[A]): IO[A] = t match {
    case Failure(err) => raiseError(err)
    case Success(a)   => pure(a)
  }

  def none[A]: IO[Option[A]] = pure(None)

  def raiseError[A](e: Throwable): IO[A] = ErrorModel(e)

  def raiseUnless(cond: Boolean)(e: => Throwable): IO[Unit] = unlessA(cond)(raiseError(e))

  def raiseWhen(cond: Boolean)(e: => Throwable): IO[Unit] = whenA(cond)(raiseError(e))

  def unlessA(cond: Boolean)(action: => IO[Unit]): IO[Unit] = if (cond) unit else suspend(action)

  def whenA(cond: Boolean)(action: => IO[Unit]): IO[Unit] = if (cond) suspend(action) else unit

  val unit: IO[Unit] = Pure(())

  private final case class Pure[+A](a: A) extends IO[A]

  private final case class Delay[+A](body: () => A) extends IO[A]

  private final case class Suspend[+A](thunk: () => IO[A]) extends IO[A]

  private final case class Map[A, +B](io: IO[A], f: A => B) extends IO[B]

  private final case class FlatMap[A, +B](io: IO[A], f: A => IO[B]) extends IO[B]

  private final case class ErrorModel(t: Throwable) extends IO[Nothing]
}
