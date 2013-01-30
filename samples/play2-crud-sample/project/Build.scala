import sbt._
import Keys._
import PlayProject._

object ApplicationBuild extends Build {

    val appName         = "play2-crud-sample"
    val appVersion      = "0.1.0-SNAPSHOT"

    val appDependencies = Seq(
        "play2-crud" % "play2-crud_2.9.1" % "0.1.0-SNAPSHOT"
    )

    val main = PlayProject(appName, appVersion, appDependencies, mainLang = JAVA).settings(
        //play2-cache repository
        resolvers += Resolver.url("play2-cache release repository", url("http://hakandilek.github.com/play2-cache/releases/"))(Resolver.ivyStylePatterns),
        resolvers += Resolver.url("play2-cache snapshot repository", url("http://hakandilek.github.com/play2-cache/snapshots/"))(Resolver.ivyStylePatterns)
    )

}
