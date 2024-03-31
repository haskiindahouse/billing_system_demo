package wiring

import cats.effect.IO
import controllers.ClientController
import services.client.ClientServiceImpl

object ClientWiring {
  lazy val clientController = new ClientController[IO](clientService)

  private val clientService = new ClientServiceImpl[IO]()

}
