package repositories

import cats.effect.IO
import model.Payments
import repositories.ClientsRepository.ec
import slick.jdbc.PostgresProfile.api._

object PaymentsRepository {

  val db = Database.forConfig("mydb")
  val payments = TableQuery[Payments]

  // Создание нового платежа
  def create(paymentData: (Int, BigDecimal, java.sql.Date, String, Option[String], Option[String])): IO[Int] = IO.fromFuture(IO {
    db.run {
      (payments.map(p => (p.clientId, p.amount, p.date, p.method, p.description, p.transactionId))
        returning payments.map(_.id)
        into ((_, id) => id)
        ) += paymentData
    }
  })

  def getAll: IO[Seq[(Int, Int, BigDecimal, java.sql.Date, String, Option[String], Option[String])]] = IO.fromFuture(IO {
    db.run(payments.result)
  })

  // Удаление платежа
  def delete(id: Int): IO[Boolean] = IO.fromFuture(IO {
    db.run {
      payments.filter(_.id === id).delete.map(_ > 0)
    }
  })
//
//  // Список всех платежей
//  def list(): IO[Seq[(Int, Int, String, BigDecimal, java.sql.Timestamp)]] = IO.fromFuture(IO {
//    db.run {
//      payments.result
//    }
//  })
}
