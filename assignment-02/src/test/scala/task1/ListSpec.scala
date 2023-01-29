package task1

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers._

class ListSpec extends AnyFlatSpec {
  "A list" should "have tail function" in {
    val list = List(1, 2, 3)
    val tailedList = List.tail(list)
    val expectedList = List(2, 3)
    assertResult(expectedList)(tailedList)
  }

  it should "have tail function working with 1 element" in {
    val list = List(1)
    val tailedList = List.tail(list)
    val expectedList = Nil
    assertResult(expectedList)(tailedList)
  }

  it should "have tail function working with empty list" in {
    val list = Nil
    val tailedList = List.tail(list)
    val expectedList = Nil
    assertResult(expectedList)(tailedList)
  }

  it should "have setHead function" in {
    val list = List(1, 2, 3)
    val newList = List.setHead(list, 4)
    val expectedList = List(4, 2, 3)
    assertResult(expectedList)(newList)
  }

  it should "have setHead function working with 1 element" in {
    val list = List(1)
    val newList = List.setHead(list, 2)
    val expectedList = List(2)
    assertResult(expectedList)(newList)
  }

  it should "have setHead function working with empty list" in {
    val list = Nil
    val newList = List.setHead(list, 1)
    val expectedList = Nil
    assertResult(expectedList)(newList)
  }

  it should "have drop function" in {
    val list = List(1, 2, 3)
    val newList = List.drop(list, 2)
    val expectedList = List(3)
    assertResult(expectedList)(newList)
  }

  it should "have drop function with n = 1" in {
    val list = List(1, 2, 3)
    val newList = List.drop(list, 1)
    val expectedList = List(2, 3)
    assertResult(expectedList)(newList)
  }

  it should "have drop function with n = 0" in {
    val list = List(1, 2, 3)
    val newList = List.drop(list, 0)
    val expectedList = List(1, 2, 3)
    assertResult(expectedList)(newList)
  }

  it should "have drop function with n < 0" in {
    val list = List(1, 2, 3)
    val newList = List.drop(list, -1)
    val expectedList = List(1, 2, 3)
    assertResult(expectedList)(newList)
  }

  it should "have drop function working with 1 element" in {
    val list = List(1)
    val newList = List.drop(list, 1)
    val expectedList = Nil
    assertResult(expectedList)(newList)
  }

  it should "have drop function working with empty list" in {
    val list = Nil
    val newList = List.drop(list, 2)
    val expectedList = Nil
    assertResult(expectedList)(newList)
  }

  it should "have dropWhile function" in {
    val list = List(2, 4, 3, 5, 6)
    val newList = List.dropWhile(list, (x: Int) => x % 2 == 0)
    val expectedList = List(3, 5, 6)
    assertResult(expectedList)(newList)
  }

  it should "have dropWhile function working with 1 element" in {
    val list = List(2)
    val newList = List.dropWhile(list, (x: Int) => x % 2 == 0)
    val expectedList = Nil
    assertResult(expectedList)(newList)
  }

  it should "have dropWhile function working with empty list" in {
    val list = Nil
    val newList = List.dropWhile(list, (x: Int) => x % 2 == 0)
    val expectedList = Nil
    assertResult(expectedList)(newList)
  }

  it should "have init function" in {
    val list = List(1, 2, 3)
    val newList = List.init(list)
    val expectedList = List(1, 2)
    assertResult(expectedList)(newList)
  }

  it should "have init function working with 1 element" in {
    val list = List(1)
    val newList = List.init(list)
    val expectedList = Nil
    assertResult(expectedList)(newList)
  }

  it should "have init function working with empty list" in {
    val list = Nil
    val newList = List.init(list)
    val expectedList = Nil
    assertResult(expectedList)(newList)
  }

  it should "have length function" in {
    val list = List(1, 2, 3)
    val length = List.length(list)
    val expectedLength = 3
    assertResult(expectedLength)(length)
  }

  it should "have length function working with 1 element" in {
    val list = List(1)
    val length = List.length(list)
    val expectedLength = 1
    assertResult(expectedLength)(length)
  }

  it should "have length function working with empty list" in {
    val list = Nil
    val length = List.length(list)
    val expectedLength = 0
    assertResult(expectedLength)(length)
  }

  it should "have map function" in {
    val list = List(1, 2, 3)
    val newList = List.map(list: List[Int])((x: Int) => x * 2)
    val expectedList = List(2, 4, 6)
    assertResult(expectedList)(newList)
  }

  it should "have map function working with 1 element" in {
    val list = List(1)
    val newList = List.map(list: List[Int])((x: Int) => x * 2)
    val expectedList = List(2)
    assertResult(expectedList)(newList)
  }

  it should "have map function working with empty list" in {
    val list = Nil
    val newList = List.map(list: List[Int])((x: Int) => x * 2)
    val expectedList = Nil
    assertResult(expectedList)(newList)
  }

  it should "have fold function" in {
    val list = List(1, 2, 3)
    val result = List.fold(list: List[Int], 0)((res: Int, x: Int) => res + x * x)
    val expectedResult = 14
    assertResult(expectedResult)(result)
  }

  it should "have fold function working with 1 element" in {
    val list = List(2)
    val result = List.fold(list: List[Int], 0)((res: Int, x: Int) => res + x * x)
    val expectedResult = 4
    assertResult(expectedResult)(result)
  }

  it should "have fold function working with empty list" in {
    val list = Nil
    val result = List.fold(list: List[Int], 0)((res: Int, x: Int) => res + x * x)
    val expectedResult = 0
    assertResult(expectedResult)(result)
  }
}
