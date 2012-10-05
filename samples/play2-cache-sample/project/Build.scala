import sbt._
import Keys._
import PlayProject._

object ApplicationBuild extends Build {

    val appName         = "play2-cache-sample"
    val appVersion      = "0.1.0-SNAPSHOT"

    val appDependencies = Seq(
        "play2-cache" % "play2-cache_2.9.1" % "0.1.0-SNAPSHOT"
    )

    val main = PlayProject(appName, appVersion, appDependencies, mainLang = JAVA).settings(
        resolvers += Resolver.url("play2-cache RELEASE repository", url("http://hakandilek.github.com/play2-cache/releases/"))(Resolver.ivyStylePatterns),
        resolvers += Resolver.url("play2-cache SNAPSHOT repository", url("http://hakandilek.github.com/play2-cache/snapshots/"))(Resolver.ivyStylePatterns),
        checksums := Nil
    )
}
