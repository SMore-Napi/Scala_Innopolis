package task1

import scala.annotation.tailrec

sealed trait List[+A] // `List` data type, parameterized on a type, `A`
// Take a note on a `+` before A. As was discussed in the lab, it denotes that the list is a covariant class
// What does it mean????????

// Let us assume, C is a subtype of B (class C() extends B).
// It means, that we can coerce any instance of C to B:
// val b: B = new C() - value b of type B
// But, is the same relationship preserved for List[C] and List[B]?
// Three answers to the question exist
// 1) Yes, the relationship is preserved:
//    This is denoted by a `+` in type argument: List[+A]:
//    val listB: List[B] = List[C](someC)
// 2) No, but actually, the relationship is inverse!
//    This is denoted by a `-` in type argument: List[-A]:
//    val listC: List[C] = List[B](someB)
// 3) There is no relationship between the lists!:
//    It this case, we leave the generic as is: List[A]:
//    val listB: List[B] = List[C](someC) - This code does not compile

case object Nil extends List[Nothing] // A `List` data constructor representing the empty list
/* This constructor of list works exactly because of a `+` in type parameter. Check the class hierarchy in Scala:
https://docs.scala-lang.org/resources/images/tour/unified-types-diagram.svg
On the picture, arrows denote the Parent-Child relationship.
For instance, AnyVal is a child of Any, Unit is a child of AnyVal, and so on.
As we can see, every class is a parent of a special class Nothing. Since List is covariant, we can
coerce Nothing to any other type in Scala. That is why, we can have List[Int] with Nil that is List[Nothing]:
val emptyList: List[Int] = Nil // (List[Nothing])
 */

/* Another data constructor, representing nonempty lists. Note that `tail` is another `List[A]`,
which may be `Nil` or another `Cons`.
 */
case class Cons[+A](head: A, tail: List[A]) extends List[A]

object List { // `List` companion object. Contains functions for creating and working with lists.
  // Pattern Matching
  val y = List(1, 2, 3) match {
    // List(1, 2, 3) can be opened as Cons(1, Cons(2, Cons(3, Nil)))
    // Remember, that in pattern matching, we can both capture variables
    // and check whether the structure of the object is correct
    case Cons(1, Cons(x, Cons(4, Nil))) => x + 2 // Will not match
    // It matches 1 as the first element of the list, then captures x = 2
    // but the third element 3 != 4
    case Cons(x, Cons(y, Cons(z, Nil))) =>
      x + y + z // Matches! Captures all values in list and returns 6case _: task1.Nil.type => ???
    case a: Cons[String] if a.head == "Hello" => a.head // Will not match
    // Remember, that we can also check types in pattern matching, and have pattern guards
    case _ => 1 // Matches, but will actually not be invoked
    // In pattern matching the first pattern that matches will actually be invoked
    // There is one pattern above, so it will not be used
  }
  // Leave a comment, which of the cases holds
  val x = List(1, 2, 3, 4, 5) match {
    // As a hint to you:
    // List(1, 2, 3, 4, 5) can be opened as a:
    // Cons(1, Cons(2, Cons(3, Cons(4, Cons(5, Nil)))))

    // does not hold since after 2 there should be 3 != 4
    case Cons(x, Cons(2, Cons(4, _))) => x
    // does not hold since the list is Cons of elements, i.e. it's not empty
    case Nil => 42
    // It will work
    case Cons(x, Cons(y, Cons(3, Cons(4, _)))) => x + y
    // It will work, but won't be invoked, since the above case will hold it
    case Cons(h, t) => h + sum(t)
    // It will work, but won't be invoked, since the above case will hold it
    case _ => 101
  }

  def sum(ints: List[Int]): Int = ints match { // A function that uses pattern matching to add up a list of integers
    case Nil => 0 // The sum of the empty list is 0.
    // Recursive case
    case Cons(x, xs) => x + sum(xs) // The sum of a list starting with `x` is `x` plus the sum of the rest of the list.
  }

  def product(ds: List[Double]): Double = ds match {
    case Nil          => 1.0
    case Cons(0.0, _) => 0.0
    case Cons(x, xs)  => x * product(xs)
  }

  def apply[A](as: A*): List[A] = // Variadic function syntax (takes 0+ arguments of the same type)
    if (as.isEmpty) Nil
    else Cons(as.head, apply(as.tail: _*))

  def append[A](a1: List[A], a2: List[A]): List[A] = {
    // Function to append two lists
    a1 match {
      case Nil        => a2 // we reached the full depth of the first list
      case Cons(h, t) => Cons(h, append(t, a2)) // traverse the first list and glue it back with a2 on the end
    }
  }

  def foldRight[A, B](as: List[A], z: B)(f: (A, B) => B): B = { // Utility functions
    // Is generally used to traverse list and perform some action on it
    as match {
      case Nil         => z
      case Cons(x, xs) => f(x, foldRight(xs, z)(f))
    }
  }

  def sum2(ns: List[Int]) = {
    // We can implement the sum function, using fold
    foldRight(ns, 0)((x, y) => x + y)
  }

  def product2(ns: List[Double]) = {
    // we can also implement product function using fold
    foldRight(ns, 1.0)(_ * _)
  } // `_ * _` is more concise notation for `(x,y) => x * y`; see sidebar

  // Remove the head of the list
  def tail[A](l: List[A]): List[A] = l match {
    case Nil         => Nil
    case Cons(_, xs) => xs
  }

  // Change the head of the list to a given head
  def setHead[A](l: List[A], h: A): List[A] = l match {
    case Nil           => Nil
    case Cons(_, rest) => Cons(h, rest)
  }

  // Drop first N elements of the list
  // just return an empty list if n > len(list)
  def drop[A](l: List[A], n: Int): List[A] = l match {
    case Nil => Nil
    case Cons(_, t) =>
      n match {
        case _ if n > 0 => drop(t, n - 1)
        case _          => l
      }
  }

  // Drop elements of the list until conditions are satisfied
  def dropWhile[A](l: List[A], f: A => Boolean): List[A] = l match {
    case Nil                         => Nil
    case Cons(head, tail) if f(head) => dropWhile(tail, f)
    case _                           => l
  }

  // Return all elements of the list except the last one
  // Use the same logic for error handling as in drop
  def init[A](l: List[A]): List[A] = l match {
    case Nil          => Nil
    case Cons(_, Nil) => Nil
//    case Cons(a, Cons(_, Nil)) => Cons(a, Nil)
    case Cons(head, tail) => Cons(head, init(tail))
  }

  // Return the length of the list
  def length[A](l: List[A]): Int = l match {
    case Nil           => 0
    case Cons(_, tail) => 1 + length(tail)
  }

  // Convert List[A] to List[B], using function f: A => B
  def map[A, B](l: List[A])(f: A => B): List[B] = l match {
    case Nil              => Nil
    case Cons(head, tail) => Cons(f(head), map(tail)(f))
  }

  // (*) EXTRA: implement stack safe fold (google annotation @tailrec. We will discuss it in labs later)
  // https://www.scala-exercises.org/scala_tutorial/tail_recursion
  @tailrec
  def fold[A, B](l: List[A], z: B)(f: (B, A) => B): B = l match {
    case Nil              => z
    case Cons(head, tail) => fold(tail, f(z, head))(f)
  }
}
