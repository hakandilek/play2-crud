import sbt._
import Keys._
import play.Project._

object ApplicationBuild extends Build {

    val appName         = "play2-crud"
    val appVersion      = "0.4.1-SNAPSHOT"

    val appDependencies = Seq(
        javaCore, javaJdbc, javaEbean,
        "play2-cache" % "play2-cache_2.10" % "0.4.0-SNAPSHOT"
    )

    val main = play.Project(appName, appVersion, appDependencies).settings(
        publishArtifact in(Compile, packageDoc) := false,
        
        //maven repository
        resolvers += "release repository" at  "http://hakandilek.github.com/maven-repo/releases/",
        resolvers += "snapshot repository" at "http://hakandilek.github.com/maven-repo/snapshots/"
    )

}
