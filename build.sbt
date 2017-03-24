name := "akka-assignment-03"

version := "1.0"

scalaVersion := "2.11.8"

libraryDependencies ++= Seq(
  "com.typesafe.akka" % "akka-actor_2.11" % "2.4.17",
  "com.typesafe.akka" % "akka-testkit_2.11" % "2.4.17",
  "org.scalatest" %% "scalatest" % "3.0.1",
  "ch.qos.logback" % "logback-classic" % "1.1.3",
  "com.typesafe.akka" % "akka-slf4j_2.11" % "2.4.17"
)

    