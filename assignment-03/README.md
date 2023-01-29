[![Open in Visual Studio Code](https://classroom.github.com/assets/open-in-vscode-c66648af7eb3fe8bc4f294546bfd86ef473780cde1dea487d3c4ff354943c9ae.svg)](https://classroom.github.com/online_ide?assignment_repo_id=9406273&assignment_repo_type=AssignmentRepo)
# Introduction to FP and Scala
## Assignment 3 - Simple Json Parser
In this assignment you can get up to 100 pt, however, there will be a bonus task which will relax grading from my side if you pull it off.

> **Warning**
> NEVER push your code to the main branch! Instead, create a new branch (`git checkout -b solutions`), add and push your code there (`git push origin solutions`) and then open a pull request from the new branch to the main branch. This is the only way.

> **Note**
> If you see something you do not know, google its documentation.
### Task 1 [30 pt]

In this assignment we are going to implement a simple Json parser, using implicits in Scala. It must be able to convert a type to a json String and convert a json String back to the type:
```scala
trait Json[A] {
  def toJson(a: A): String
  def fromJson(json: String): Either[ParseError, A]
}
```

Aside from bare minimal knowledge about generics in Java, you should know only about how they are searched as implicit values. Scala searches them not by general type Json[A], but using the concrete type that is required in the function:
```scala
def toJson[A](a: A)(implicit json: Json[A]) = ???

toJson[String]("MyString") // Actually searches for Json[String] in implicit scopes
```

As such, it is possible to make Json[String], Json[Int], ... in a single implicit scope. Types Json[String] and Json[Int] are not equal!


For the first task, your parser should be able to parse class Course. For the whole assignment, I will not watch for a correct indentation, you can actually one-line the whole Json.

### Task 2 [60 pt]
Implement a Json parser for a case class Student in folder Task2. You can add necessary implicits either to Json.scala file or companion object of class Student

### Task 3 [10 pt]
Using our parser for students is a bit crude:

```scala
import task2.Student
import task1.Json

val student: Student = ???
val studentJson = "???"
val studentToJson = Json.toJson(student)
val studentFromJson = Json.fromJson(studentToJson)
```

Make it possible to use methods toJson() and fromJson() directly from an instance of Student without modifying Student's code:

```scala
val student: Student = ???
val studentToJson = student.toJson(student)
val studentFromJson = student.fromJson(studentToJson)
```

### Bonus!

Can you make json strings pretty?
