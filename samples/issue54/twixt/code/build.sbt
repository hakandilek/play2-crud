name := "twixt54"

version := "0.1.0-SNAPSHOT"

scalaVersion := "2.11.4"

libraryDependencies ++= Seq(
  javaCore, javaJdbc, javaEbean,
  "play2-crud" %% "play2-crud" % "0.7.4-SNAPSHOT",
  "play2-crud" %% "play2-crud" % "0.7.4-SNAPSHOT" classifier "assets"
)

lazy val root = (project in file(".")).enablePlugins(PlayJava)

//resolvers += "release repository" at  "http://hakandilek.github.com/maven-repo/releases/"
//
//resolvers += "snapshot repository" at "http://hakandilek.github.com/maven-repo/snapshots/"



