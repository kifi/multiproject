import sbt._
import Keys._
import play.Project._

object ApplicationBuild extends Build {

  val branch = "git rev-parse --abbrev-ref HEAD".!!.trim
  val commit = "git rev-parse --short HEAD".!!.trim
  val buildTime = (new java.text.SimpleDateFormat("yyyyMMdd-hhmmss")).format(new java.util.Date())

  val appVersion = "$branch-$commit-$buildTime"

  val commonDependencies = Seq(
    // Add your project dependencies here,
    jdbc,
    anorm
  )
  val serviceADependencies = Seq() // You can have service specific dependencies
  val serviceBDependencies = Seq()


  val common = play.Project("common", appVersion, commonDependencies).settings(
    // Add common settings here      
  )

  val serviceA = play.Project("serviceA", appVersion, commonDependencies ++ serviceADependencies).settings(
    // Add serviceA settings here      
  ).dependsOn(common).aggregate(common)

  val serviceB = play.Project("serviceB", appVersion, commonDependencies ++ serviceBDependencies).settings(
    // Add serviceB settings here      
  ).dependsOn(common).aggregate(common)


  val multiProject = play.Project("multiproject", appVersion, commonDependencies ++ serviceADependencies ++ serviceBDependencies).settings(
    // This project runs both services together, which is mostly useful in development mode.
  ).dependsOn(common, serviceA, serviceB).aggregate(common, serviceA, serviceB)

}
