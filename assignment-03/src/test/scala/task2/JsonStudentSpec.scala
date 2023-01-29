package task2

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers._
import task1.Json.StudentToJson
import task1.{Course, Json}

class JsonStudentSpec extends AnyFlatSpec {
  "A json" should "convert Boolean true" in {
    val json = Json.toJson(true)
    assertResult("true")(json)
  }

  it should "convert Boolean false" in {
    val json = Json.toJson(false)
    assertResult("false")(json)
  }

  it should "convert List of Courses" in {
    val courses = List(Course("Scala"), Course("DevOps"), Course("Total Virtualization"))
    val json = Json.toJson(courses)
    val expectedJson = "[{\"name\": \"Scala\"}, {\"name\": \"DevOps\"}, {\"name\": \"Total Virtualization\"}]"
    assertResult(expectedJson)(json)
  }

  it should "convert List of Courses with 1 element" in {
    val courses = List(Course("Scala"))
    val json = Json.toJson(courses)
    val expectedJson = "[{\"name\": \"Scala\"}]"
    assertResult(expectedJson)(json)
  }

  it should "convert empty List of Courses" in {
    val courses = List[Course]()
    val json = Json.toJson(courses)
    val expectedJson = "[]"
    assertResult(expectedJson)(json)
  }

  it should "convert Student" in {
    val courses = List(Course("Scala"), Course("DevOps"), Course("Total Virtualization"))
    val roman = Student(
      name = "Roman",
      age = 22,
      // I don't know what is the translation of this field :)
      is_conditioned = true,
      courses = courses
    )
    val json = Json.toJson(roman)
    val expectedJson =
      "{\"name\": \"Roman\", \"age\": 22, \"is_conditioned\": true, \"courses\": [{\"name\": \"Scala\"}, {\"name\": \"DevOps\"}, {\"name\": \"Total Virtualization\"}]}"
    assertResult(expectedJson)(json)
  }

  "A Student instance" should "have toJson() method" in {
    val courses = List(Course("Scala"), Course("DevOps"), Course("Total Virtualization"))
    val roman = Student(name = "Roman", age = 22, is_conditioned = true, courses = courses)
    val json: String = roman.toJson
    val expectedJson =
      "{\"name\": \"Roman\", \"age\": 22, \"is_conditioned\": true, \"courses\": [{\"name\": \"Scala\"}, {\"name\": \"DevOps\"}, {\"name\": \"Total Virtualization\"}]}"
    assertResult(expectedJson)(json)
  }

  "A Student instance" should "have toJson(other: Student) method" in {
    val courses = List(Course("Scala"), Course("DevOps"), Course("Total Virtualization"))
    val roman = Student(
      name = "Roman",
      age = 22,
      // I don't know what is the translation of this field :)
      is_conditioned = true,
      courses = courses
    )
    val anotherStudent = Student(
      name = "Ivan",
      age = 21,
      // I don't know what is the translation of this field :)
      is_conditioned = false,
      courses = List(Course("Scala"))
    )
    val json: String = roman.toJson(anotherStudent)
    println(json)
    val expectedJson =
      "{\"name\": \"Ivan\", \"age\": 21, \"is_conditioned\": false, \"courses\": [{\"name\": \"Scala\"}]}"
    assertResult(expectedJson)(json)
  }
}
