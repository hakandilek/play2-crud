import sbt._
import Keys._
import play.Play.autoImport._
import PlayKeys._

object ApplicationBuild extends Build {

    val appName         = "play2-crud"
    val appVersion      = "0.7.4-SNAPSHOT"
    val appScalaVersion = "2.11.6"

    val appDependencies = Seq(
        javaCore, javaJdbc, javaEbean, cache,
        "org.webjars" % "jasny-bootstrap" % "2.3.0-j5",
        "org.webjars" % "json2" % "20110223",
        "com.google.inject" % "guice" % "4.0-beta4"
    )

    val root = Project(appName, file(".")).enablePlugins(play.PlayScala).settings(
        version := appVersion,
        scalaVersion := appScalaVersion,
        libraryDependencies ++= appDependencies,
        publishArtifact in(Compile, packageDoc) := false,
        packagedArtifacts += ((artifact in playPackageAssets).value -> playPackageAssets.value),
        publishMavenStyle := true,
        publishTo <<= version { (v: String) =>
            if (v.trim.endsWith("SNAPSHOT"))
                Some(Resolver.file("file",  new File( "../../maven-repo/snapshots" )) )
            else
                Some(Resolver.file("file",  new File( "../../maven-repo/releases" )) )
        }
    )

}
