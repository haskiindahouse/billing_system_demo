import sbt.*

object deps {

  val postgresql = "org.postgresql" % "postgresql" % "42.7.1"
  object slick {
    val slickPg = "com.github.tminglei" %% "slick-pg" % "0.20.3"
    val slickPgPlayJson = "com.github.tminglei" %% "slick-pg_play-json" % "0.20.3"
    val slickHikaricp = "com.typesafe.slick" %% "slick-hikaricp" % "3.3.3"
    val slick = "com.typesafe.slick" %% "slick" % "3.3.3"
  }
  object http4s {
    val http4sDsl = "org.http4s" %% "http4s-dsl" % "0.23.25"
    val http4sBlazeServer = "org.http4s" %% "http4s-blaze-server" % "0.23.6"
    val http4sCirce = "org.http4s" %% "http4s-circe" % "0.23.25"
    val http4sServer = "org.http4s" %% "http4s-server" % "0.23.25"
  }

  object circe {
    val circeGeneric = "io.circe" %% "circe-generic" % "0.14.6"
    val circeLiteral = "io.circe" %% "circe-literal" % "0.14.6"
  }

  object log {
    val log4cats = "org.typelevel" %% "log4cats-slf4j" % "2.6.0"
    val slf4j = "org.slf4j" % "slf4j-api" % "2.0.9"
    val logback ="ch.qos.logback" % "logback-classic" % "1.4.14"
  }

  object cats {
    val catsEffect = "org.typelevel" %% "cats-effect" % "3.3.14"
    val catsCore = "org.typelevel" %% "cats-core" % "2.10.0"
  }

  object swagger {
    val swaggerAnnotations = "io.swagger" % "swagger-annotations" % "1.6.12"
  }
}

object scalac {
  val v2    = "2.13.10"
}
