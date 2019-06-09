name := "SocialMediaStream_Query"

version := "0.1"

scalaVersion := "2.11.12"

resolvers ++= Seq(
  Resolver.sonatypeRepo("releases")
)

lazy val dependencies = Seq(
  "com.typesafe.scala-logging" %% "scala-logging" % Versions.ScalaLogging,
  "org.slf4j" % "slf4j-api" % Versions.Slf4j,
  "org.slf4j" % "log4j-over-slf4j" % Versions.Slf4j,
  "ch.qos.logback" % "logback-classic" % Versions.Logback,
  "com.typesafe" % "config" % Versions.TypesafeConfig,
  "com.datastax.spark" %% "spark-cassandra-connector" % Versions.SparkCassandraConnector,
  "org.apache.spark" % "spark-core_2.11" % Versions.SparkCore
)

excludeDependencies := Seq(
  "org.slf4j" % "slf4j-log4j12",
  "log4j",
  "org.apache.logging.log4j"
)

libraryDependencies ++= dependencies
