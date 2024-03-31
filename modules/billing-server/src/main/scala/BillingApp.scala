import cats.effect._
import org.typelevel.log4cats.Logger
import org.typelevel.log4cats.slf4j.Slf4jLogger
import org.http4s.blaze.server.BlazeServerBuilder
import services.BillingService

object BillingApp extends IOApp.Simple{

  implicit val logger: Logger[IO] = Slf4jLogger.getLogger[IO]

  override def run: IO[Unit] = {
    for {
      _ <- logger.info("Starting billing server...")
      _ <- BlazeServerBuilder[IO]
            .bindHttp(9999, "0.0.0.0")
            .withHttpApp(BillingService.app)
            .serve
            .compile
            .drain
    } yield ()

  }

}