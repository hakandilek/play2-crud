import sbt._
import Keys._
import PlayProject._

object ApplicationBuild extends Build {

    val appName         = "play2-crud"
    val appVersion      = "0.1.0-SNAPSHOT"

    val appDependencies = Seq(
        "play2-cache" % "play2-cache_2.9.1" % "0.3.0-SNAPSHOT"
    )

    val main = PlayProject(appName, appVersion, appDependencies, mainLang = JAVA).settings(
        //maven repository
        resolvers += Resolver.url("release repository", url("http://hakandilek.github.com/maven-repo/releases/"))(Resolver.ivyStylePatterns),
        resolvers += Resolver.url("snapshot repository", url("http://hakandilek.github.com/maven-repo/snapshots/"))(Resolver.ivyStylePatterns)
    )

}
