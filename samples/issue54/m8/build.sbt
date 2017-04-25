name := "issue54"

version := "0.7.4-SNAPSHOT"

scalaVersion := "2.11.4"

libraryDependencies ++= Seq(
  javaCore, javaJdbc, javaEbean,
  "play2-crud" %% "play2-crud" % "0.7.4-SNAPSHOT",
  "play2-crud" %% "play2-crud" % "0.7.4-SNAPSHOT" classifier "assets",
  "twixt54" % "twixt54_2.11" % "0.1.0-SNAPSHOT"
)

lazy val root = (project in file(".")).enablePlugins(PlayJava)

//resolvers += "release repository" at  "http://hakandilek.github.com/maven-repo/releases/"
//
//resolvers += "snapshot repository" at "http://hakandilek.github.com/maven-repo/snapshots/"

//for heroku, uncomment next line and do 
// play eclipse clean compile
//resolvers += Resolver.url("Mettle Repository", url("http://ianrae.github.io/snapshot/"))(Resolver.ivyStylePatterns)


