import sbt._
import Keys._
import play.Project._

object ApplicationBuild extends Build {

    val appName         = "play2-crud"
    val appVersion      = "0.6.0-SNAPSHOT"

    val appDependencies = Seq(
        javaCore, javaJdbc, javaEbean
    )

    lazy val play2cache = play.Project("play2-cache", appVersion, appDependencies, path = file("modules/play2-cache/project-code")).settings(
        publishArtifact in(Compile, packageDoc) := false,
        publishMavenStyle := true,
        publishTo <<= version { (v: String) =>
        	if (v.trim.endsWith("SNAPSHOT"))
    			Some(Resolver.file("file",  new File( "../../maven-repo/snapshots" )) )
			else
    			Some(Resolver.file("file",  new File( "../../maven-repo/releases" )) )
        }
    )

    val main = play.Project(appName, appVersion, appDependencies).settings(
        publishArtifact in(Compile, packageDoc) := false,
        publishMavenStyle := true,
        publishTo <<= version { (v: String) =>
        	if (v.trim.endsWith("SNAPSHOT"))
    			Some(Resolver.file("file",  new File( "../../maven-repo/snapshots" )) )
			else
    			Some(Resolver.file("file",  new File( "../../maven-repo/releases" )) )
        }
    ).dependsOn(play2cache).aggregate(play2cache)

}
