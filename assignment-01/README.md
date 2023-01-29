[![Open in Visual Studio Code](https://classroom.github.com/assets/open-in-vscode-c66648af7eb3fe8bc4f294546bfd86ef473780cde1dea487d3c4ff354943c9ae.svg)](https://classroom.github.com/online_ide?assignment_repo_id=9223451&assignment_repo_type=AssignmentRepo)
# Introduction to FP and Scala
## Assignment 1
In this assignment you can get up to 105 pt, however, if you get more than 100 pt, extra points will be dropped and you will be awarded with 100 pt. Tasks with additional points are marked with (*). 

The purpose of this assignment is to get hands-on experience with sbt and testing frameworks.

This assignment is an sbt project consisting of several projects. Review lab 01 materials if you do not remember how to work with sbt.

> **Warning**
> NEVER push your code to the main branch! Instead, create a new branch (`git checkout -b solutions`), add and push your code there (`git push origin solutions`) and then open a pull request from the new branch to the main branch. This is the only way.

> **Note**
> Please make sure that the source files are formatted with scalafmt, otherwise the test pipeline will fail. You can format all the source file with `scalafmtAll` command in sbt.

> **Note**
> If you see something you do not know, google its documentation.
### Task 1 [40 pt + 5 extra]
[20 pt + 5 extra] In the lecture you had a task to finish an implementation for rational number class. Finish the implementation asked in the lecture and implement several more methods (`<`, `<=`, `>`, `>=`, `equals`, `toInt`, `toLong`, `toFloat`, `toDouble`). Also add possibility to instanciate a rational number from a string (*). 

[20 pt] Write tests with Scalatest, do not forget to cover all the edge cases and possible exceptions. Do not forget that you can also provide property-based tests (Scalacheck is recommended).

```scala
class Rational(n: Int, d: Int) {
  require(d != 0)
  private val g = gcd(n.abs, d.abs)
  val numer = n / g
  val denom = d / g
  def this(n: Int) = this(n, 1)
  def this(s: String) = this(
    ???,
    ???
  ) /* Add the code. Examples: "1/7", "5", "3.4". Throw NumberFormatException if wrong format. [5 extra pt]*/

  def toInt: Int = ??? /* add the code */
  def toLong: Long = ??? /* add the code */
  def toFloat: Float = ??? /* add the code */
  def toDouble: Double = ??? /* add the code */

  def +(that: Rational): Rational = new Rational(numer * that.denom + that.numer * denom, denom * that.denom)
  def +(i: Int): Rational = ??? /* add the code */

  def -(that: Rational): Rational =
    new Rational(numer * that.denom - that.numer * denom, denom * that.denom)
  def -(i: Int): Rational = ??? /* add the code */

  def *(that: Rational): Rational =
    new Rational(numer * that.numer, denom * that.denom)
  def *(i: Int): Rational = ??? /* add the code */

  def /(that: Rational): Rational =
    new Rational(numer * that.denom, denom * that.numer)
  def /(i: Int): Rational = ??? /* add the code */

  def <(that: Rational): Boolean = ??? /* add the code */
  def <=(that: Rational): Boolean = ??? /* add the code */
  def >(that: Rational): Boolean = ??? /* add the code */
  def >=(that: Rational): Boolean = ??? /* add the code */

  override def equals(that: Any): Boolean =
    ??? /* Add the code. Hint: remember how equals is usually implemented in Java. Any is like Object in Java: it resides on top of the type hierarchy. */

  override def toString = numer + "/" + denom
  private def gcd(a: Int, b: Int): Int = if (b == 0) a else gcd(b, a % b)
}
```

### Task 2 [60 pt]
[30 pt] Implement simple currency converter that can convert one currency to another. 

Money cannot have negative amount. 

Money operations cannot be applied to two money objects with different currencies. 

Create an `object Money`, a so called companion object, and implement method `def apply(amount: BigDecimal, currency: String)` in it. It will be a `Money` constructor, that should check:

- `amount` should be positive or throw `MoneyAmountShouldBePositiveException`
- `currency` should be contained in `CurrencyRate.SupportedCurrencies`, otherwise throw `UnsupportedCurrencyException`

Exchange cannot be called on the same currencies

[30 pt] Write tests with Scalatest covering different outcomes and exceptions. 

> **Note**
> 
> In this task you will encounter (case) classes, exceptions and objects. From the course pre-requisites: you should be familiar with classes, exceptions, inheritance and other OOP constructs. Case classes are like usual classes but with public immutable fields (i.e. val's) written in the constructor (see Money). Methods inside objects are similar to static methods in Java. You will learn these concepts later in the course, for now they are not important.
```scala
object Currencies {
    val SupportedCurrencies = Set("RUB", "USD", "EUR")
}

class MoneyAmountShouldBePositiveException extends Exception
class UnsupportedCurrencyException extends Exception
class WrongCurrencyException extends Exception

case class Money private (amount: BigDecimal, currency: String) {
    def +(other: Money): Money = ???
    def -(other: Money): Money = ???
    def isSameCurrency(other: Money): Boolean = ???
}

object Money {
    def apply(amount: BigDecimal, currency: String) = ???
}


class CurrencyConverter private (ratesDictionary: Map[String, Map[String, BigDecimal]]) {
    def exchange(money: Money, toCurrency: String): Money = ??? 
}
object CurrencyConverter {
    import Currencies.SupportedCurrencies

    def apply(ratesDictionary: Map[String, Map[String, BigDecimal]]) = {
        val fromCurrencies = ratesDictionary.keys
        val toCurrencies = ratesDictionary.values
        if (fromCurrencies.toSet.subsetOf(SupportedCurrencies) && toCurrencies.forall(_.keys.toSet.subsetOf(SupportedCurrencies)))
          new CurrencyConverter(ratesDictionary)
        else throw new UnsupportedCurrencyException
        
    }
}
```