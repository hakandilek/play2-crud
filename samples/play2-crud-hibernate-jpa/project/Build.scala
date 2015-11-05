import sbt._
import Keys._
import play.Play.autoImport._
import PlayKeys._

object ApplicationBuild extends Build {

    val appName         = "play2-crud-hibernate-jpa"
    val appVersion      = "0.8.0-SNAPSHOT"
    val appScalaVersion = "2.11.6"

    val appDependencies = Seq(
        javaCore, javaJdbc, 
		javaJpa.exclude("org.hibernate.javax.persistence", "hibernate-jpa-2.0-api"),
		"org.hibernate" % "hibernate-entitymanager" % "5.0.3.Final",
		
        ("play2-crud" %% "play2-crud" % "0.8.0-SNAPSHOT")
        	.exclude("com.typesafe.play", "play-java-ebean_2.11")
        	.exclude("org.avaje.ebeanorm", "avaje-ebeanorm")
        	.exclude("org.avaje.ebeanorm", "avaje-ebeanorm-agent"),
        ("play2-crud" %% "play2-crud" % "0.8.0-SNAPSHOT" classifier "assets")
        	.exclude("com.typesafe.play", "play-java-ebean_2.11")
        	.exclude("org.avaje.ebeanorm", "avaje-ebeanorm")
        	.exclude("org.avaje.ebeanorm", "avaje-ebeanorm-agent"),
        "org.mockito" % "mockito-core" % "1.9.5"
    )

    val main = Project(appName, file(".")).enablePlugins(play.PlayJava).settings(
        version := appVersion,
        scalaVersion := appScalaVersion,
        libraryDependencies ++= appDependencies,
        //maven repository
        resolvers += "release repository" at  "http://hakandilek.github.com/maven-repo/releases/",
        resolvers += "snapshot repository" at "http://hakandilek.github.com/maven-repo/snapshots/"
    )
}