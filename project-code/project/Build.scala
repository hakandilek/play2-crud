import sbt._
import Keys._
import play.Play.autoImport._
import PlayKeys._

object ApplicationBuild extends Build {

    val appName         = "play2-crud"
    val appVersion      = "0.7.4-SNAPSHOT"
    val appScalaVersion = "2.11.1"

    val appDependencies = Seq(
        javaCore, javaJdbc, javaEbean, cache,
        "com.google.inject" % "guice" % "3.0"
    )

    val root = Project(appName, file(".")).enablePlugins(play.PlayScala).settings(
        version := appVersion,
        scalaVersion := appScalaVersion,
        libraryDependencies ++= appDependencies,
        publishArtifact in(Compile, packageDoc) := false,
        publishMavenStyle := true,
        publishTo <<= version { (v: String) =>
        	if (v.trim.endsWith("SNAPSHOT"))
    			Some(Resolver.file("file",  new File( "../../maven-repo/snapshots" )) )
			else
    			Some(Resolver.file("file",  new File( "../../maven-repo/releases" )) )
        }
    )

}
