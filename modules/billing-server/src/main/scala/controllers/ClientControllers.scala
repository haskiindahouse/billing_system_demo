package controllers

import cats.implicits.toSemigroupKOps
import wiring.ClientWiring.clientController

object ClientControllers {
  lazy val route = clientControllers.reduce(_ <+> _)

  // internal

  private val clientControllers = Seq(
    clientController.route
  )
}
