import Dependencies._

ThisBuild / scalaVersion     := "3.1.3"
ThisBuild / version          := "0.1.0-SNAPSHOT"
ThisBuild / organization     := "com.dth"
ThisBuild / organizationName := "dth"

lazy val root = (project in file("."))
  .settings(
    name := "dth-subscription-system",
    libraryDependencies += scalaTest % Test,
    javacOptions ++= Seq("-source", "11", "-target", "11"),
    Compile / mainClass := Some("com.dth.runner.Runner"),
    assembly / mainClass := Some("com.dth.runner.Runner")
  )
// See https://www.scala-sbt.org/1.x/docs/Using-Sonatype.html for instructions on how to publish to Sonatype.
