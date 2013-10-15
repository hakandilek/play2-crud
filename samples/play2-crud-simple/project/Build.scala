import sbt._
import Keys._
import play.Project._

object ApplicationBuild extends Build {

    val appName         = "play2-crud-simple"
    val appVersion      = "0.7.0-SNAPSHOT"

    val appDependencies = Seq(
        javaCore, javaJdbc, javaEbean,
        "play2-crud" % "play2-crud_2.10" % "0.7.0-SNAPSHOT"
    )

    val main = play.Project(appName, appVersion, appDependencies).settings(
        //maven repository
        resolvers += "release repository" at  "http://hakandilek.github.com/maven-repo/releases/",
        resolvers += "snapshot repository" at "http://hakandilek.github.com/maven-repo/snapshots/"
    )

}
