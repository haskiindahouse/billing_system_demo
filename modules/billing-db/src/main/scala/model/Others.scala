package model

import slick.jdbc.PostgresProfile.api._

class Services(tag: Tag) extends Table[(Int, String, String, BigDecimal)](tag, "services") {
  def id = column[Int]("id", O.PrimaryKey, O.AutoInc)
  def name = column[String]("name")
  def periodicity = column[String]("periodicity")
  def cost = column[BigDecimal]("cost")

  def * = (id, name, periodicity, cost)
}

class Payments(tag: Tag) extends Table[(Int, Int, BigDecimal, java.sql.Date, String, Option[String],  Option[String])](tag, "payments") {
  def id = column[Int]("id", O.PrimaryKey, O.AutoInc)
  def clientId = column[Int]("client_id")
  def amount = column[BigDecimal]("amount")
  def date = column[java.sql.Date]("date")
  def method = column[String]("method")
  def description = column[Option[String]]("description")
  def transactionId = column[Option[String]]("transaction_id")

  def * = (id, clientId, amount, date, method, description, transactionId)

  def client = foreignKey("client_fk", clientId, TableQuery[Clients])(_.id)
}

class Expenses(tag: Tag) extends Table[(Int, Int, Int, BigDecimal, java.sql.Timestamp)](tag, "expenses") {
  def id = column[Int]("id", O.PrimaryKey, O.AutoInc)
  def clientId = column[Int]("client_id")
  def serviceId = column[Int]("service_id")
  def amount = column[BigDecimal]("amount")
  def date = column[java.sql.Timestamp]("date")

  def * = (id, clientId, serviceId, amount, date)

  def client = foreignKey("client_fk", clientId, TableQuery[Clients])(_.id)
  def service = foreignKey("service_fk", serviceId, TableQuery[Services])(_.id)
}

class Adjustments(tag: Tag) extends Table[(Int, Int, String, BigDecimal, java.sql.Timestamp)](tag, "adjustments") {
  def id = column[Int]("id", O.PrimaryKey, O.AutoInc)
  def clientId = column[Int]("client_id")
  def adjustmentType = column[String]("type")
  def amount = column[BigDecimal]("amount")
  def date = column[java.sql.Timestamp]("date")

  def * = (id, clientId, adjustmentType, amount, date)

  def client = foreignKey("client_fk", clientId, TableQuery[Clients])(_.id)
}
