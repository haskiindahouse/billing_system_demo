import org.typelevel.scalacoptions.ScalacOptions
import deps.*
import sbt.Project.projectToRef

ThisBuild / organization     := "ru.haskiindahouse"
ThisBuild / organizationName := "haskiindahouse"
ThisBuild / scalaVersion     := scalac.v2
ThisBuild / assemblyMergeStrategy := {
  case x => MergeStrategy.first
}
lazy val scala3Versions      = List(scalac.v2)

lazy val commonSettings = Seq(
  tpolecatExcludeOptions ++= Set(ScalacOptions.privateKindProjector),
  tpolecatScalacOptions ++= Set(
    ScalacOptions.source213,
    ScalacOptions.privatePartialUnification
  ),
  tpolecatExcludeOptions += ScalacOptions.privateWarnUnusedNoWarn,
  Test / tpolecatExcludeOptions ++= Set(
    ScalacOptions.warnUnusedLocals,
    ScalacOptions.fatalWarnings,
    ScalacOptions.privateWarnUnusedLocals
  ),
  publish / skip := true
)

val db = "billing-db"
lazy val `billing-db` = (project in file(s"modules/$db"))
  .settings(commonSettings)
  .settings(
    name                := db,
    libraryDependencies ++= Seq(
      postgresql,
      slick.slick,
      slick.slickHikaricp,
      slick.slickPg,
      slick.slickPgPlayJson,
      cats.catsEffect,
      cats.catsCore,
      log.log4cats,
      log.slf4j
    )
  )

val server = "billing-server"
lazy val `billing-server` = (project in file(s"modules/$server"))
  .dependsOn(`billing-db`)
  .settings(commonSettings)
  .settings(
  name := server,
    libraryDependencies ++= Seq(
      http4s.http4sBlazeServer,
      http4s.http4sCirce,
      http4s.http4sDsl,
      http4s.http4sServer,
      circe.circeGeneric,
      circe.circeLiteral,
      cats.catsEffect,
      cats.catsCore,
      log.log4cats,
      log.slf4j,
      log.logback,
      swagger.swaggerAnnotations,
    ),
    assembly / assemblyJarName := "server.jar",
    assembly / mainClass := Some("BillingApp")
)

lazy val allModules = Seq(
    `billing-db`,
    `billing-server`
  ).map(projectToRef)

lazy val `root` = (project in file("."))
  .settings(
    name                := "root",
    publish / skip      := true,
    libraryDependencies ++= Seq(
      http4s.http4sBlazeServer,
      http4s.http4sCirce,
      http4s.http4sDsl,
      http4s.http4sServer,
      circe.circeGeneric,
      circe.circeLiteral,
      cats.catsEffect,
      cats.catsCore,
      log.log4cats,
      log.slf4j,
      log.logback,
      swagger.swaggerAnnotations,
      postgresql
    )
  )
  .aggregate(allModules *)

