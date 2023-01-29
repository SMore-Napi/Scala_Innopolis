package task1

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers._

class RationalSpec extends AnyFlatSpec {

  "A rational number" should "convert to Int" in {
    val r = new Rational("5/1")
    assertResult(5)(r.toInt)
  }

  it should "convert to Int without fractional" in {
    val r = new Rational("5/2")
    assertResult(2)(r.toInt)
  }

  it should "convert to Long" in {
    val r = new Rational("5/1")
    assertResult(5)(r.toLong)
  }

  it should "convert to Long without fractional" in {
    val r = new Rational("5/2")
    assertResult(2)(r.toLong)
  }

  it should "convert to Float" in {
    val r = new Rational("5/2")
    assertResult(2.5)(r.toFloat)
  }

  it should "convert to Double" in {
    val r = new Rational("5/2")
    assertResult(2.5)(r.toDouble)
  }

  it should "support + operator" in {
    val r1 = new Rational("5/2")
    val r2 = new Rational("4/7")
    val expected = new Rational(43, 14)
    assertResult(expected)(r1 + r2)
  }

  it should "support + operator with number" in {
    val r1 = new Rational("5/2")
    val expected = new Rational(15, 2)
    assertResult(expected)(r1 + 5)
  }

  it should "support - operator" in {
    val r1 = new Rational("5/2")
    val r2 = new Rational("4/7")
    val expected = new Rational(27, 14)
    assertResult(expected)(r1 - r2)
  }

  it should "support - operator with number" in {
    val r1 = new Rational("5/2")
    val expected = new Rational(-5, 2)
    assertResult(expected)(r1 - 5)
  }

  it should "support * operator" in {
    val r1 = new Rational("5/2")
    val r2 = new Rational("4/7")
    val expected = new Rational(10, 7)
    assertResult(expected)(r1 * r2)
  }

  it should "support * operator with number" in {
    val r1 = new Rational("5/2")
    val expected = new Rational(25, 2)
    assertResult(expected)(r1 * 5)
  }

  it should "support / operator" in {
    val r1 = new Rational("5/2")
    val r2 = new Rational("4/7")
    val expected = new Rational(35, 8)
    assertResult(expected)(r1 / r2)
  }

  it should "support / operator with number" in {
    val r1 = new Rational("5/2")
    val expected = new Rational(1, 2)
    assertResult(expected)(r1 / 5)
  }

  it should "support < operator" in {
    val r1 = new Rational("5/2")
    val r2 = new Rational("4/7")
    assertResult(false)(r1 < r2)
  }

  it should "support <= operator" in {
    val r1 = new Rational("5/2")
    val r2 = new Rational("4/7")
    assertResult(false)(r1 <= r2)
  }

  it should "support > operator" in {
    val r1 = new Rational("5/2")
    val r2 = new Rational("4/7")
    assertResult(true)(r1 > r2)
  }

  it should "support >= operator" in {
    val r1 = new Rational("5/2")
    val r2 = new Rational("4/7")
    assertResult(true)(r1 >= r2)
  }

  it should "support == operator" in {
    val r1 = new Rational("5/2")
    val r2 = new Rational(5, 2)
    assertResult(true)(r1 == r2)
  }

  it should "support toString" in {
    val r = new Rational(5, 2)
    assertResult("5/2")(r.toString)
  }

  it should "have constructor with 2 Ints" in {
    val r = new Rational(5, 2)
    assertResult(5)(r.numer)
    assertResult(2)(r.denom)
  }

  it should "have constructor with 1 Int argument" in {
    val r = new Rational(5)
    assertResult(5)(r.numer)
    assertResult(1)(r.denom)
  }

  it should "have constructor with String argument" in {
    val r = new Rational("5")
    assertResult(5)(r.numer)
    assertResult(1)(r.denom)
  }

  it should "throw exception with String argument if value is not a number" in {
    assertThrows[NumberFormatException] {
      new Rational("hello")
    }
  }

  it should "have constructor with String / argument" in {
    val r = new Rational("5/2")
    assertResult(5)(r.numer)
    assertResult(2)(r.denom)
  }

  it should "throw exception with String / argument if denominator is not a number" in {
    assertThrows[NumberFormatException] {
      new Rational("5/hello")
    }
  }

  it should "throw exception with String / argument if numerator is not a number" in {
    assertThrows[NumberFormatException] {
      new Rational("hello/3")
    }
  }

  it should "throw exception with String / argument if contains several /" in {
    assertThrows[NumberFormatException] {
      new Rational("3/3/3")
    }
  }

  it should "throw exception with String / argument if denominator is not Int" in {
    assertThrows[NumberFormatException] {
      new Rational("5/3.2")
    }
  }

  it should "throw exception with String / argument if numerator is not Int" in {
    assertThrows[NumberFormatException] {
      new Rational("24.2/3")
    }
  }

  it should "have constructor with String . argument" in {
    val r = new Rational("5.2")
    assertResult(26)(r.numer)
    assertResult(5)(r.denom)
  }

  it should "throw exception with String . argument if integer part is not number" in {
    assertThrows[NumberFormatException] {
      new Rational("hello.435")
    }
  }

  it should "throw exception with String . argument if floating part is not number" in {
    assertThrows[NumberFormatException] {
      new Rational("24.hello")
    }
  }

  it should "throw exception with String . argument if floating part negative" in {
    assertThrows[NumberFormatException] {
      new Rational("24.-45")
    }
  }

  it should "throw exception with String . argument if contains several ." in {
    assertThrows[NumberFormatException] {
      new Rational("3.3.2")
    }
  }
}
