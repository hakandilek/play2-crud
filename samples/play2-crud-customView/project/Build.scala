import sbt._
import Keys._
import play.Play.autoImport._
import PlayKeys._

object ApplicationBuild extends Build {

    val appName         = "play2-crud-customView"
    val appVersion      = "0.7.4-SNAPSHOT"
    val appScalaVersion = "2.11.1"

    val appDependencies = Seq(
        javaCore, javaJdbc, javaEbean,
        "play2-crud" % "play2-crud_2.11" % "0.7.4-SNAPSHOT",
        "play2-crud" % "play2-crud_2.11" % "0.7.4-SNAPSHOT" classifier "assets"
    )

    val main = Project(appName, file(".")).enablePlugins(play.PlayJava).settings(
        version := appVersion,
        scalaVersion := appScalaVersion,
        libraryDependencies ++= appDependencies,
        //maven repository
        resolvers += "release repository" at  "http://hakandilek.github.com/maven-repo/releases/",
        resolvers += "snapshot repository" at "http://hakandilek.github.com/maven-repo/snapshots/"
    )

}
