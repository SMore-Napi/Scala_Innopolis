package task1

class Rational(n: Int, d: Int) {
  require(d != 0)
  private val g = gcd(n.abs, d.abs)
  val numer = n / g
  val denom = d / g

  def this(n: Int) = this(n, 1)

  def this(s: String) = this(
    if (s.contains('/')) {
      val x = s.split('/')
      if (x.length != 2) {
        throw new NumberFormatException(s"The Rational number does not follow the right format: $s.")
      }
      try {
        x(0).toInt
      } catch {
        case _: Throwable =>
          throw new NumberFormatException(s"The Rational number does not follow the right format: $s.")
      }
    } else if (s.contains('.')) {
      try {
        val x = s.split('.')
        if (x.length != 2) {
          throw new NumberFormatException(s"The Rational number does not follow the right format: $s.")
        }
        if (x(1).toInt > 0) {
          x(0).toInt * 10 + x(1).toInt
        } else {
          throw new NumberFormatException(s"The Rational number does not follow the right format: $s.")
        }
      } catch {
        case _: Throwable =>
          throw new NumberFormatException(s"The Rational number does not follow the right format: $s.")
      }
    } else {
      try {
        s.toInt
      } catch {
        case _: Throwable =>
          throw new NumberFormatException(s"The Rational number does not follow the right format: $s.")
      }
    },
    if (s.contains('/')) {
      val x = s.split('/')
      try {
        x(1).toInt
      } catch {
        case _: Throwable =>
          throw new NumberFormatException(s"The Rational number does not follow the right format: $s.")
      }
    } else if (s.contains('.')) 10
    else 1
  ) /* Add the code. Examples: "1/7", "5", "3.4". Throw NumberFormatException if wrong format. [5 extra pt]*/

  /* add the code */
  def toLong: Long = toInt.toLong

  def toInt: Int = numer / denom

  /* add the code */
  def toFloat: Float = numer.toFloat / denom.toFloat

  /* add the code */
  def toDouble: Double = numer.toDouble / denom.toDouble /* add the code */

  def +(that: Rational): Rational = new Rational(numer * that.denom + that.numer * denom, denom * that.denom)

  def +(i: Int): Rational = new Rational(numer + i * denom, denom) /* add the code */

  def -(that: Rational): Rational =
    new Rational(numer * that.denom - that.numer * denom, denom * that.denom)

  def -(i: Int): Rational = new Rational(numer - i * denom, denom) /* add the code */

  def *(that: Rational): Rational =
    new Rational(numer * that.numer, denom * that.denom)

  def *(i: Int): Rational = new Rational(numer * i, denom) /* add the code */

  def /(that: Rational): Rational =
    new Rational(numer * that.denom, denom * that.numer)

  def /(i: Int): Rational = new Rational(numer, denom * i) /* add the code */

  /* add the code */
  def <=(that: Rational): Boolean = this < that || this == that

  /* add the code */
  def >=(that: Rational): Boolean = this > that || this == that /* add the code */

  /* add the code */
  def >(that: Rational): Boolean = that < this

  def <(that: Rational): Boolean = numer * that.denom < denom * that.numer

  override def equals(that: Any): Boolean = {
    that match {
      case t: Rational => numer * t.denom == denom * t.numer
      case _           => false
    }
  } /* Add the code. Hint: remember how equals is usually implemented in Java. Any is like Object in Java: it resides on top of the type hierarchy. */

  override def toString = numer + "/" + denom

  private def gcd(a: Int, b: Int): Int = if (b == 0) a else gcd(b, a % b)
}
