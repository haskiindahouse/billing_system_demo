package repositories

import cats.effect.IO
import model.Services
import slick.jdbc.PostgresProfile.api._

object ServicesRepository {

  val db = Database.forConfig("mydb")
  val services = TableQuery[Services]

  // Создание новой услуги
  def create(service: (String, String, BigDecimal)): IO[Int] = IO.fromFuture(IO {
    db.run {
      (services.map(s => (s.name, s.periodicity, s.cost))
        returning services.map(_.id)
        into ((_, id) => id)
        ) += service
    }
  })

  // Получение услуги по ID
  def read(id: Int): IO[Option[(Int, String, String, BigDecimal)]] = IO.fromFuture(IO {
    db.run {
      services.filter(_.id === id).result.headOption
    }
  })

  // Обновление услуги
  def update(id: Int, updatedService: (String, String, BigDecimal)): IO[Int] = IO.fromFuture(IO {
    db.run {
      val query = for (service <- services if service.id === id) yield (service.name, service.periodicity, service.cost)
      query.update((updatedService._1, updatedService._2, updatedService._3))
    }
  })

  // Удаление услуги
  def delete(id: Int): IO[Int] = IO.fromFuture(IO {
    db.run {
      services.filter(_.id === id).delete
    }
  })

  // Список всех услуг
  def list(): IO[Seq[(Int, String, String, BigDecimal)]] = IO.fromFuture(IO {
    db.run {
      services.result
    }
  })
}
