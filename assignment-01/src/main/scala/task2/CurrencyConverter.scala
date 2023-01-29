package task2

object Currencies {
  val SupportedCurrencies = Set("RUB", "USD", "EUR")
}

class MoneyAmountShouldBePositiveException extends Exception

class UnsupportedCurrencyException extends Exception

class WrongCurrencyException extends Exception

case class Money private (amount: BigDecimal, currency: String) {
  def +(other: Money): Money = if (isSameCurrency(other)) {
    val res = amount + other.amount
    if (res.compareTo(BigDecimal("0")) <= 0) {
      throw new MoneyAmountShouldBePositiveException()
    }
    new Money(res, currency)
  } else {
    throw new WrongCurrencyException()
  }

  def -(other: Money): Money = if (isSameCurrency(other)) {
    val res = amount - other.amount
    if (res.compareTo(BigDecimal("0")) <= 0) {
      throw new MoneyAmountShouldBePositiveException()
    }
    new Money(res, currency)
  } else {
    throw new WrongCurrencyException()
  }

  def isSameCurrency(other: Money): Boolean = if (other.currency == currency) true else false
}

object Money {
  def apply(amount: BigDecimal, currency: String) = {
    if (amount.compareTo(BigDecimal("0")) <= 0) {
      throw new MoneyAmountShouldBePositiveException()
    }
    if (!Currencies.SupportedCurrencies.contains(currency)) {
      throw new UnsupportedCurrencyException()

    }
    new Money(amount, currency)
  }
}

class CurrencyConverter private (ratesDictionary: Map[String, Map[String, BigDecimal]]) {
  def exchange(money: Money, toCurrency: String): Money = Money(
    amount = money.amount * ratesDictionary(money.currency)(toCurrency),
    currency = toCurrency
  )
}

object CurrencyConverter {

  import Currencies.SupportedCurrencies

  def apply(ratesDictionary: Map[String, Map[String, BigDecimal]]) = {
    val fromCurrencies = ratesDictionary.keys
    val toCurrencies = ratesDictionary.values
    if (
      fromCurrencies.toSet
        .subsetOf(SupportedCurrencies) && toCurrencies.forall(_.keys.toSet.subsetOf(SupportedCurrencies))
    )
      new CurrencyConverter(ratesDictionary)
    else throw new UnsupportedCurrencyException
  }
}
