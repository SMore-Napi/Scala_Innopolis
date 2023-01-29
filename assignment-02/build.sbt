lazy val root = (project in file(".")).
  settings(
    inThisBuild(List(
      organization := "com.example",
      scalaVersion := "2.13.10"
    )),
    name := "assignment01"
  )

libraryDependencies += "org.scalatest" %% "scalatest" % "3.2.9" % Test
