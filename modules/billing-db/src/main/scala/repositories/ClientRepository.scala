package repositories

import cats.effect.IO
import model.Clients
import slick.jdbc.PostgresProfile.api._

import scala.concurrent.ExecutionContext

object ClientsRepository {

  implicit val ec: ExecutionContext = scala.concurrent.ExecutionContext.global

  val db = Database.forConfig("mydb")

  def create(client: (BigDecimal, BigDecimal, String, String, String, String, String)): IO[Int] = {
    IO.fromFuture(IO {
      db.run {
        (clients.map(c => (c.balance, c.limit, c.status, c.name, c.email, c.address, c.phoneNumber))
          returning clients.map(_.id)
          into ((_, id) => id)
          ) += client
      }
    })
  }

  def getAll: IO[Seq[(Int, BigDecimal, BigDecimal, String, String, String, String, String)]] = IO.fromFuture(IO {
    db.run(clients.result)
  })

  def update(clientId: Int, clientData: (Option[String], Option[String], Option[String])): IO[Boolean] = {
    val (emailOpt, phoneOpt, statusOpt) = clientData

    val updateQuery = clients.filter(_.id === clientId)
      .map(client => (client.email, client.phoneNumber, client.status))

    val updateAction = updateQuery.result.headOption.flatMap {
      case Some(existingClient) =>
        val updateValues = (
          emailOpt.getOrElse(existingClient._1),
          phoneOpt.getOrElse(existingClient._2),
          statusOpt.getOrElse(existingClient._3)
        )
        updateQuery.update(updateValues).map(_ => true)
      case None =>
        DBIO.successful(false)
    }.transactionally

    IO.fromFuture(IO(db.run(updateAction))).handleErrorWith(_ => IO.pure(false))
  }

  def delete(clientId: Int): IO[Boolean] = IO.fromFuture(IO {
    db.run(clients.filter(_.id === clientId).delete).map(_ > 0)
  })

  def getStatus(clientId: Int): IO[Option[String]] = IO.fromFuture(IO {
    db.run(clients.filter(_.id === clientId).map(_.status).result.headOption)
  })

  def getBalance(clientId: Int): IO[Option[(BigDecimal, BigDecimal)]] = IO.fromFuture(IO {
    db.run(clients.filter(_.id === clientId).map(c => (c.balance, c.limit)).result.headOption)
  })

  val clients = TableQuery[Clients]
}
