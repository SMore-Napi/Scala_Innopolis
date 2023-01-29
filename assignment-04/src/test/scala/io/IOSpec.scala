import io.IO
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers._

import scala.util.{Failure, Success}

class IOSpec extends AnyFlatSpec {
  "IO" should "support map" in {
    val io = IO[String]("hello")
    val res = io.map(x => x.length).unsafeRunSync()
    assertResult(5)(res)
  }

  it should "support flatMap" in {
    val io = IO[String]("hello")
    val res = io.flatMap(x => IO(x.length)).unsafeRunSync()
    assertResult(5)(res)
  }

  it should "support *>" in {
    val io1 = IO[String]("hello")
    val io2 = IO[Int](5)
    val res = io1.*>(io2).unsafeRunSync()
    assertResult(5)(res)
  }

  it should "support as" in {
    val io1 = IO[String]("hello")
    val io2 = IO[Int](5)
    val res = io1.as(io2).unsafeRunSync()
    assertResult(5)(res.unsafeRunSync())
  }

  it should "support void" in {
    val io = IO[String]("hello")
    val res: Unit = io.void.unsafeRunSync()
    assertResult(())(res)
  }

  it should "support attempt with value" in {
    val io = IO[String]("hello")
    val res = io.attempt.unsafeRunSync()
    assertResult(Right("hello"))(res)
  }

  it should "support attempt with exception" in {
    val exception = new IllegalArgumentException()
    val io = IO.raiseError(exception)
    val res = io.attempt.unsafeRunSync()
    assertResult(Left[Throwable, String](exception))(res)
  }

  it should "support option with value" in {
    val io = IO[String]("hello")
    val res = io.option.unsafeRunSync()
    assertResult(Some("hello"))(res)
  }

  it should "support option with exception" in {
    val io = IO.raiseError(new IllegalArgumentException())
    val res = io.option.unsafeRunSync()
    assertResult(None)(res)
  }

  it should "support handleErrorWith" in {
    val io = IO.raiseError(new IllegalArgumentException())
    val res = io.handleErrorWith(_ => IO("hello")).unsafeRunSync()
    assertResult("hello")(res)
  }

  it should "support redeem" in {
    val io = IO("hello").void
    val res = io.redeem(_ => 2, _ => 3).unsafeRunSync()
    assertResult(res)(3)
  }

  it should "support redeemWith" in {
    val io = IO("hello").void
    val res = io.redeemWith(_ => IO(2), _ => IO(3)).unsafeRunSync()
    assertResult(res)(3)
  }

  it should "support apply" in {
    val io = IO.apply("hello")
    val res = io.unsafeRunSync()
    assertResult("hello")(res)
  }

  it should "support suspend" in {
    val io = IO("hello")
    val ioSuspended = IO.suspend(io)
    val res = ioSuspended.unsafeRunSync()
    assertResult("hello")(res)
  }

  it should "support delay" in {
    val ioDelayed = IO.delay("hello")
    val res = ioDelayed.unsafeRunSync()
    assertResult("hello")(res)
  }

  it should "support pure" in {
    val pureResult = IO.pure("hello")
    val res = pureResult.unsafeRunSync()
    assertResult("hello")(res)
  }

  it should "support fromEither with exception" in {
    val io = IO.fromEither(Left[Throwable, String](new IllegalArgumentException()))
    assertThrows[IllegalArgumentException] {
      io.unsafeRunSync()
    }
  }

  it should "support fromEither with value" in {
    val io = IO.fromEither(Right("hello"))
    val res = io.unsafeRunSync()
    assertResult("hello")(res)
  }

  it should "support fromOption with value" in {
    val io = IO.fromOption(Some("hello"))(new IllegalArgumentException())
    val res = io.unsafeRunSync()
    assertResult("hello")(res)
  }

  it should "support fromOption without value" in {
    val io = IO.fromOption(None)(new IllegalArgumentException())
    assertThrows[IllegalArgumentException] {
      io.unsafeRunSync()
    }
  }

  it should "support fromTry with value" in {
    val io = IO.fromTry(Success("hello"))
    val res = io.unsafeRunSync()
    assertResult("hello")(res)
  }

  it should "support fromTry with exception" in {
    val io = IO.fromTry(Failure(new IllegalArgumentException()))
    assertThrows[IllegalArgumentException] {
      io.unsafeRunSync()
    }
  }

  it should "support none" in {
    val io = IO.none
    val res = io.unsafeRunSync()
    assertResult(None)(res)
  }

  it should "support raiseError" in {
    val io = IO.raiseError(new IllegalArgumentException())
    assertThrows[IllegalArgumentException] {
      io.unsafeRunSync()
    }
  }

  it should "support raiseUnless false" in {
    val io = IO.raiseUnless(cond = false)(new IllegalArgumentException())
    assertThrows[IllegalArgumentException] {
      io.unsafeRunSync()
    }
  }

  it should "support raiseUnless true" in {
    val io = IO.raiseUnless(cond = true)(new IllegalArgumentException())
    val res: Unit = io.unsafeRunSync()
    assertResult(())(res)
  }

  it should "support raiseWhen true" in {
    val io = IO.raiseWhen(cond = true)(new IllegalArgumentException())
    assertThrows[IllegalArgumentException] {
      io.unsafeRunSync()
    }
  }

  it should "support raiseWhen false" in {
    val io = IO.raiseWhen(cond = false)(new IllegalArgumentException())
    val res: Unit = io.unsafeRunSync()
    assertResult(())(res)
  }

  it should "support unlessA true" in {
    val io = IO.unlessA(cond = true)(IO.unit)
    val res: Unit = io.unsafeRunSync()
    assertResult(())(res)
  }

  it should "support unlessA false" in {
    val io = IO.unlessA(cond = false)(IO.unit)
    val res: Unit = io.unsafeRunSync()
    assertResult(())(res)
  }

  it should "support whenA true" in {
    val io = IO.whenA(cond = true)(IO.unit)
    val res: Unit = io.unsafeRunSync()
    assertResult(())(res)
  }

  it should "support whenA false" in {
    val io = IO.whenA(cond = false)(IO.unit)
    val res: Unit = io.unsafeRunSync()
    assertResult(())(res)
  }

  it should "support unit" in {
    val io = IO.unit
    val res: Unit = io.unsafeRunSync()
    assertResult(())(res)
  }

  it should "work with side effects" in {
    var i = ""
    val appendString1 = IO {
      val s = "Hello, "
      println(s"Append string: $s")
      i = i + s
    }
    val appendString2 = IO {
      val s = "world! "
      println(s"Append string: $s")
      i = i + s
    }
    val appendString3 = IO {
      val s = "and Scala"
      println(s"Append string: $s")
      i = i + s
    }
    val io = appendString1 *> appendString2 *> appendString3
    assertResult("")(i)
    io.unsafeRunSync()
    assertResult("Hello, world! and Scala")(i)
  }

  it should "not throw stack overflow exception" in {
    var value = 0
    var io = IO(value)
    0 to 1000 foreach { i =>
      io = io.flatMap(_ => {
        value += 1
        IO(value)
      })
    }
    io.unsafeRunSync()
    assertResult(value)(1001)
  }
}
