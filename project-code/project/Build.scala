import sbt._
import Keys._
import PlayProject._

object ApplicationBuild extends Build {

    val appName         = "play2-cache"
    val appVersion      = "0.3.0-SNAPSHOT"

    val appDependencies = Seq(
        // no dependencies
    )

    val main = PlayProject(appName, appVersion, appDependencies, mainLang = JAVA).settings(
    )

}
