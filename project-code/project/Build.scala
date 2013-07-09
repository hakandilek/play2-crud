import sbt._
import Keys._
import play.Project._

object ApplicationBuild extends Build {

    val appName         = "play2-crud"
    val appVersion      = "0.6.0-SNAPSHOT"

    val appDependencies = Seq(
        javaCore, javaJdbc, javaEbean,
        "play2-cache" % "play2-cache_2.10" % "0.4.0-SNAPSHOT"
    )

    val main = play.Project(appName, appVersion, appDependencies).settings(
        publishArtifact in(Compile, packageDoc) := false,
        
        //maven repository
        resolvers += "release repository" at  "http://hakandilek.github.com/maven-repo/releases/",
        resolvers += "snapshot repository" at "http://hakandilek.github.com/maven-repo/snapshots/",
        
        publishMavenStyle := true,
        publishTo <<= version { (v: String) =>
        	if (v.trim.endsWith("SNAPSHOT"))
    			Some(Resolver.file("file",  new File( "../../maven-repo/snapshots" )) )
			else
    			Some(Resolver.file("file",  new File( "../../maven-repo/releases" )) )
        }
    )

}
