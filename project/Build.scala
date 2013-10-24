import sbt._
import Keys._
import play.Project._

object ApplicationBuild extends Build {

  val branch = "git rev-parse --abbrev-ref HEAD".!!.trim
  val commit = "git rev-parse --short HEAD".!!.trim
  val buildTime = (new java.text.SimpleDateFormat("yyyyMMdd-HHmmss")).format(new java.util.Date())
  val appVersion = "%s-%s-%s".format(branch, commit, buildTime)

  val commonDependencies = Seq(
    // Add your project dependencies here,
    jdbc,
    anorm
  )
  val serviceADependencies = Seq() // You can have service specific dependencies
  val serviceBDependencies = Seq()

  val scalaBuildOptions = Seq("-unchecked", "-deprecation", "-feature", "-language:reflectiveCalls",
    "-language:implicitConversions", "-language:postfixOps", "-language:dynamics","-language:higherKinds",
    "-language:existentials", "-language:experimental.macros", "-Xmax-classfile-name", "140")


  val common = play.Project("common", appVersion, commonDependencies, path = file("modules/common")).settings(
    // Add common settings here
    scalacOptions ++= scalaBuildOptions,
    sources in doc in Compile := List(),
    javaOptions in Test += "-Dconfig.resource=common-application.conf"
  )

  val serviceA = play.Project("serviceA", appVersion, commonDependencies ++ serviceADependencies, path = file("modules/serviceA")).settings(
    // Add serviceA settings here      
    scalacOptions ++= scalaBuildOptions,
    sources in doc in Compile := List(),
    javaOptions in Test += "-Dconfig.resource=serviceA-application.conf"
  ).dependsOn(common % "test->test;compile->compile").aggregate(common)

  val serviceB = play.Project("serviceB", appVersion, commonDependencies ++ serviceBDependencies, path = file("modules/serviceB")).settings(
    // Add serviceB settings here
    scalacOptions ++= scalaBuildOptions,
    sources in doc in Compile := List(),
    javaOptions in Test += "-Dconfig.resource=serviceB-application.conf"
  ).dependsOn(common % "test->test;compile->compile").aggregate(common)


  // The default SBT project is based on the first project alphabetically. To force sbt to use this one,
  // we prefit it with 'aaa'
  val aaaMultiProject = play.Project("multiproject", appVersion, commonDependencies ++ serviceADependencies ++ serviceBDependencies).settings(
    // This project runs both services together, which is mostly useful in development mode.
    scalacOptions ++= scalaBuildOptions,
    sources in doc in Compile := List()
  ).dependsOn(common % "test->test;compile->compile", serviceA % "test->test;compile->compile", serviceB % "test->test;compile->compile").aggregate(common, serviceA, serviceB)

}
