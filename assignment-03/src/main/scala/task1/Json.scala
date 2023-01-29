package task1

import task2.Student

import scala.language.implicitConversions

trait Json[A] {
  // This [A] - is called generic. For now you should know only the stuff you know about them from java
  def toJson(a: A): String

  //  def fromJson(json: String): Either[ParseError, A]
}

object Json {
  // This thing will have type Json[Int]
  // If we decide to implement Json[String], for instance,
  // scala will not argue with us that the same implicit scope has two Json[A]
  // as type Json[Int] != Json[String]
  implicit val intJson: Json[Int] = new Json[Int] {
    // Making an anonymous class toJson. As we specified type parameter A = Int,
    // Now, in all functions it is going to need ints instead of some abstract A
    override def toJson(a: Int): String = a.toString

    //    override def fromJson(json: String): Either[ParseError, Int] = {
    //      try {
    //        Right(json.toInt)
    //      } catch {
    //        case _: Throwable => Left(IntError)
    //      }
    //    }
  }

  implicit val stringJson: Json[String] = (a: String) => s"\"$a\""

  implicit val booleanJson: Json[Boolean] = (a: Boolean) => if (a) "true" else "false"

  implicit val courseJson: Json[Course] = new Json[Course] {

    override def toJson(a: Course): String = s"{\"name\": ${Json.toJson(a.name)}}"
  }

  implicit val listJson: Json[List[Course]] = new Json[List[Course]] {

    override def toJson(a: List[Course]): String = s"[${a.map(Json.toJson(_)).mkString(", ")}]"
  }

  implicit val studentJson: Json[Student] = new Json[Student] {

    override def toJson(a: Student): String = s"{${List(
      s"\"name\": ${Json.toJson(a.name)}",
      s"\"age\": ${Json.toJson(a.age)}",
      s"\"is_conditioned\": ${Json.toJson(a.is_conditioned)}",
      s"\"courses\": ${Json.toJson(a.courses)}"
    ).mkString(", ")}}"
  }

  implicit class StudentToJson(val s: Student) extends AnyVal {
    def toJson: String = Json.toJson(s)

    /*
     In GitHub Assignment there was the following example:
     val studentToJson = student.toJson(student)
     So, I added that function just in case
     Even though in Telegram there was a discussion about using it as student.toJson()
     */
    def toJson(other: Student): String = Json.toJson(other)
  }

  // in this case, if we call this function with A = Int,
  // notice, that scala will search for an implicit value
  // Json[Int] in the implicit scopes:
  // toJson[Int](a: Int)(implicit jsonify: Json[Int])
  def toJson[A](a: A)(implicit jsonify: Json[A]): String = {
    jsonify.toJson(a)
  }

  //  def fromJson[A](json: String)(implicit jsonify: Json[A]): Either[ParseError, A] = {
  //    jsonify.fromJson(json)
  //  }
}
