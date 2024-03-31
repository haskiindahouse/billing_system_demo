package services

import controllers.ClientControllers
import org.http4s.server.middleware.CORS

object BillingService {

  lazy val app = CORS.policy
    .withAllowOriginAll
    .withAllowCredentials(false)
    .apply(ClientControllers.route
    .orNotFound)

}
