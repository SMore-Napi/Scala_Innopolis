package task1

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers._

class JsonCourseSpec extends AnyFlatSpec {
  "A json" should "convert Int" in {
    val json = Json.toJson(12)
    assertResult("12")(json)
  }

  it should "convert String" in {
    val json = Json.toJson("Hello")
    assertResult("\"Hello\"")(json)
  }

  it should "convert Course" in {
    val course = Course("Intro to the most interesting course: Scala")
    val json = Json.toJson(course)
    assertResult("{\"name\": \"Intro to the most interesting course: Scala\"}")(json)
  }
}
