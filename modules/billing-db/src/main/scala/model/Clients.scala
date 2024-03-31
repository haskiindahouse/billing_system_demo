package model

import slick.jdbc.PostgresProfile.api._

class Clients(tag: Tag) extends Table[(Int, BigDecimal, BigDecimal, String, String, String, String, String)](tag, "clients") {
  def id = column[Int]("id", O.PrimaryKey, O.AutoInc)
  def balance = column[BigDecimal]("balance")
  def limit = column[BigDecimal]("limit")
  def status = column[String]("status")
  def name = column[String]("name")
  def email = column[String]("email")
  def address = column[String]("address")
  def phoneNumber = column[String]("phone_number")

  def * = (id, balance, limit, status, name, email, address, phoneNumber)
}
