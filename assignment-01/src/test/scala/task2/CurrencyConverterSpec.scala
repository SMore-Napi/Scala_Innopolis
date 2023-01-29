package task2

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers._

class CurrencyConverterSpec extends AnyFlatSpec {

  "A Money" should "support RUB" in {
    val m = Money(32, "RUB")
    assertResult(32)(m.amount)
    assertResult("RUB")(m.currency)
  }

  it should "support USD" in {
    val m = Money(32, "USD")
    assertResult(32)(m.amount)
    assertResult("USD")(m.currency)
  }

  it should "support EUR" in {
    val m = Money(32, "EUR")
    assertResult(32)(m.amount)
    assertResult("EUR")(m.currency)
  }

  it should "support only RUB, USD, and EUR" in {
    assertThrows[UnsupportedCurrencyException] {
      Money(32, "hahah")
    }
  }

  it should "have positive amount" in {
    assertThrows[MoneyAmountShouldBePositiveException] {
      Money(0, "EUR")
    }
  }

  it should "have positive amount 2" in {
    assertThrows[MoneyAmountShouldBePositiveException] {
      Money(-23, "EUR")
    }
  }

  it should "support + operator" in {
    val m1 = Money(32, "RUB")
    val m2 = Money(1, "RUB")
    val sum = m1 + m2
    assertResult(33)(sum.amount)
    assertResult("RUB")(sum.currency)
  }

  it should "throw exception if + operator is applied for different currencies" in {
    val m1 = Money(32, "RUB")
    val m2 = Money(1, "USD")
    assertThrows[WrongCurrencyException] {
      m1 + m2
    }
  }

  it should "support - operator" in {
    val m1 = Money(32, "RUB")
    val m2 = Money(1, "RUB")
    val sum = m1 - m2
    assertResult(31)(sum.amount)
    assertResult("RUB")(sum.currency)
  }

  it should "throw exception if - operator is applied for different currencies" in {
    val m1 = Money(32, "RUB")
    val m2 = Money(1, "USD")
    assertThrows[WrongCurrencyException] {
      m1 - m2
    }
  }

  it should "support currency comparison" in {
    val m1 = Money(32, "RUB")
    val m2 = Money(1, "RUB")
    assertResult(true)(m1.isSameCurrency(m2))
  }

  it should "support currency comparison 2" in {
    val m1 = Money(32, "RUB")
    val m2 = Money(1, "USD")
    assertResult(false)(m1.isSameCurrency(m2))
  }

  "A CurrencyConverter" should "exchange from RUB to USD" in {
    val ratesDictionary = Map(
      "RUB" -> Map(
        "USD" -> BigDecimal(0.5),
        "EUR" -> BigDecimal(0.1)
      ),
      "USD" -> Map(
        "RUB" -> BigDecimal(2),
        "EUR" -> BigDecimal(5)
      ),
      "EUR" -> Map(
        "USD" -> BigDecimal(5),
        "RUB" -> BigDecimal(10)
      )
    )
    val currencyConverter = CurrencyConverter(ratesDictionary)
    val m1 = Money(10, "RUB")
    val m2 = currencyConverter.exchange(m1, "USD")
    assertResult("USD")(m2.currency)
    assertResult(5)(m2.amount)
  }

  it should "exchange from RUB to EUR" in {
    val ratesDictionary = Map(
      "RUB" -> Map(
        "USD" -> BigDecimal(0.5),
        "EUR" -> BigDecimal(0.1)
      ),
      "USD" -> Map(
        "RUB" -> BigDecimal(2),
        "EUR" -> BigDecimal(5)
      ),
      "EUR" -> Map(
        "USD" -> BigDecimal(5),
        "RUB" -> BigDecimal(10)
      )
    )
    val currencyConverter = CurrencyConverter(ratesDictionary)
    val m1 = Money(10, "RUB")
    val m2 = currencyConverter.exchange(m1, "EUR")
    assertResult("EUR")(m2.currency)
    assertResult(1)(m2.amount)
  }

  it should "exchange from USD to RUB" in {
    val ratesDictionary = Map(
      "RUB" -> Map(
        "USD" -> BigDecimal(0.5),
        "EUR" -> BigDecimal(0.1)
      ),
      "USD" -> Map(
        "RUB" -> BigDecimal(2),
        "EUR" -> BigDecimal(5)
      ),
      "EUR" -> Map(
        "USD" -> BigDecimal(5),
        "RUB" -> BigDecimal(10)
      )
    )
    val currencyConverter = CurrencyConverter(ratesDictionary)
    val m1 = Money(10, "USD")
    val m2 = currencyConverter.exchange(m1, "RUB")
    assertResult("RUB")(m2.currency)
    assertResult(20)(m2.amount)
  }

  it should "exchange from USD to EUR" in {
    val ratesDictionary = Map(
      "RUB" -> Map(
        "USD" -> BigDecimal(0.5),
        "EUR" -> BigDecimal(0.1)
      ),
      "USD" -> Map(
        "RUB" -> BigDecimal(2),
        "EUR" -> BigDecimal(5)
      ),
      "EUR" -> Map(
        "USD" -> BigDecimal(5),
        "RUB" -> BigDecimal(10)
      )
    )
    val currencyConverter = CurrencyConverter(ratesDictionary)
    val m1 = Money(10, "USD")
    val m2 = currencyConverter.exchange(m1, "EUR")
    assertResult("EUR")(m2.currency)
    assertResult(50)(m2.amount)
  }

  it should "exchange from EUR to RUB" in {
    val ratesDictionary = Map(
      "RUB" -> Map(
        "USD" -> BigDecimal(0.5),
        "EUR" -> BigDecimal(0.1)
      ),
      "USD" -> Map(
        "RUB" -> BigDecimal(2),
        "EUR" -> BigDecimal(5)
      ),
      "EUR" -> Map(
        "USD" -> BigDecimal(5),
        "RUB" -> BigDecimal(10)
      )
    )
    val currencyConverter = CurrencyConverter(ratesDictionary)
    val m1 = Money(10, "EUR")
    val m2 = currencyConverter.exchange(m1, "RUB")
    assertResult("RUB")(m2.currency)
    assertResult(100)(m2.amount)
  }

  it should "exchange from EUR to USD" in {
    val ratesDictionary = Map(
      "RUB" -> Map(
        "USD" -> BigDecimal(0.5),
        "EUR" -> BigDecimal(0.1)
      ),
      "USD" -> Map(
        "RUB" -> BigDecimal(2),
        "EUR" -> BigDecimal(5)
      ),
      "EUR" -> Map(
        "USD" -> BigDecimal(5),
        "RUB" -> BigDecimal(10)
      )
    )
    val currencyConverter = CurrencyConverter(ratesDictionary)
    val m1 = Money(10, "EUR")
    val m2 = currencyConverter.exchange(m1, "USD")
    assertResult("USD")(m2.currency)
    assertResult(50)(m2.amount)
  }
}
