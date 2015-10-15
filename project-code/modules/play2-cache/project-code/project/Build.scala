import sbt._
import Keys._
import play.Project._

object ApplicationBuild extends Build {

    val appName         = "play2-cache"
    val appVersion      = "0.4.0-SNAPSHOT"
    val appScalaVersion = "2.11.6"

    val appDependencies = Seq(
       	javaCore, javaJdbc, javaEbean
    )

    val main = play.Project(appName, appVersion, appDependencies).settings(
        publishArtifact in(Compile, packageDoc) := false
    )

}
