name := """crud"""

version := "0.7.4"

lazy val crud = (project in file(".")).enablePlugins(PlayScala)

scalaVersion := "2.11.6"

libraryDependencies ++= Seq(
  javaCore,
  javaJpa.exclude("org.hibernate.javax.persistence", "hibernate-jpa-2.0-api"),
  cache,
  "org.hibernate" % "hibernate-entitymanager" % "4.3.10.Final",
  "org.springframework.data" % "spring-data-jpa" % "1.8.1.RELEASE",
  "org.webjars" %% "webjars-play" % "2.3.0-1",
  "org.webjars" % "jasny-bootstrap" % "2.3.0-j5",
  "org.webjars" % "json2" % "20110223"
)