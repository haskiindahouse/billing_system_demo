package services.client

import cats.effect.{LiftIO, Sync}
import cats.implicits.{toFlatMapOps, toFunctorOps}
import model.ClientControllerDTO._

import java.time.LocalDate
import repositories.{ClientsRepository, PaymentsRepository}

trait ClientService[F[_]] {
  def addClient(clientData: AddClientRequest): F[ClientResponse]
  def updateClient(clientId: String, clientData: ClientUpdateRequest): F[Boolean]
  def deleteClient(clientId: String): F[Boolean]
  def getClientStatus(clientId: String): F[Option[ClientStatus]]
  def getClientBalance(clientId: String): F[Option[ClientBalance]]
  def addPayment(clientId: String, payment: Payment): F[Int]
  def deletePayment(clientId: String, paymentId: String): F[Boolean]
  def setupAutoDebit(clientId: String, scheduleDetails: ScheduleDetailsRequest): F[ScheduleDetailsResponse]
  def cancelAutoDebit(clientId: String, scheduleId: String): F[Boolean]
  def getClients: F[Seq[ClientResponse]]
  def getPayments: F[Seq[Payment]]
}

// ATTENTION! Я ЗАМОКАЛ ВСЕ ПОКА ЧТО, ПОЙДУ ФРОНТ ДЕЛАТЬ
class ClientServiceImpl[F[_]: Sync: LiftIO] extends ClientService[F] {
  override def addClient(clientData: AddClientRequest): F[ClientResponse] = {
    LiftIO[F].liftIO(
      ClientsRepository.create((
        BigDecimal(0),
        BigDecimal(0),
        ClientStatus.Connected.toString,
        clientData.name,
        clientData.email,
        "",
        clientData.phone
      ))
    ).flatMap { clientId =>
      Sync[F].pure(ClientResponse(
        clientId.toString,
        ClientBalance(0, 0),
        ClientStatus.Connected,
      ))
    }
  }

  override def updateClient(clientId: String, clientData: ClientUpdateRequest): F[Boolean] = {
    LiftIO[F].liftIO(
        ClientsRepository.update(clientId.toInt, (clientData.phone, clientData.email, Some(clientData.clientStatus.toString)))
    ).flatMap(Sync[F].pure)
  }

  override def deleteClient(clientId: String): F[Boolean] = {
    LiftIO[F].liftIO(
      ClientsRepository.delete(clientId.toInt)
    ).flatMap(Sync[F].pure)
  }

  override def getClientStatus(clientId: String): F[Option[ClientStatus]] = {
    LiftIO[F].liftIO(
      ClientsRepository.getStatus(clientId.toInt)
    )
    .map {
      case Some(statusStr) => ClientStatus.fromString(statusStr)
      case None => None
    }
    .flatMap(Sync[F].pure)
  }

  override def getClientBalance(clientId: String): F[Option[ClientBalance]] = {
    LiftIO[F].liftIO(
      ClientsRepository.getBalance(clientId.toInt).map {
        case Some((currentBalance, limit)) => Some(ClientBalance(currentBalance, limit))
        case None => None
      }
    )
    .flatMap(Sync[F].pure)
  }

  override def addPayment(clientId: String, payment: Payment): F[Int] = {
    LiftIO[F].liftIO(
      PaymentsRepository.create((
        payment.clientId.toInt,
        payment.amount,
        java.sql.Date.valueOf(LocalDate.now()),
        payment.method,
        payment.description,
        payment.transactionId
    )))
    .flatMap(Sync[F].pure)
  }

  override def deletePayment(clientId: String, paymentId: String): F[Boolean] = {
    LiftIO[F].liftIO(
      PaymentsRepository.delete(paymentId.toInt)
    ).flatMap(Sync[F].pure)
  }

  override def getPayments: F[Seq[Payment]] = {
    LiftIO[F].liftIO(
      PaymentsRepository.getAll.map { paymentsSeq =>
        paymentsSeq.map { case (id, clientId, amount, date, method, description, transactionId) =>
          Payment(
            id = Some(id.toString),
            clientId = clientId.toString,
            amount = amount,
            date = date.toLocalDate,
            method = method,
            description = description,
            transactionId = transactionId
          )
        }
      }
    )
    .flatMap(Sync[F].pure)
  }

  override def getClients: F[Seq[ClientResponse]] = {
    LiftIO[F].liftIO(
      ClientsRepository.getAll.map { clientsSeq =>
        clientsSeq.map { case (id, balance, limit, status, name, email, address, phoneNumber) =>
          ClientResponse(
            id.toString,
            ClientBalance(balance, limit),
            ClientStatus.fromString(status).get,
            name = Some(name),
            email = Some(email),
            address = Some(address),
            phone = Some(phoneNumber)
          )
        }
      }
    ).flatMap(Sync[F].pure)
  }

  override def setupAutoDebit(clientId: String, scheduleDetails: ScheduleDetailsRequest): F[ScheduleDetailsResponse] = {
    // Настроить автоматическое списание
    Sync[F].pure(ScheduleDetailsResponse(
      LocalDate.now(),
      LocalDate.now().toString,
      12,
      Some("12"),
      Some("13"),
      "123456789",
      "123456789"
    ))
  }

  override def cancelAutoDebit(clientId: String, scheduleId: String): F[Boolean] = {
    Sync[F].pure(true)
  }
}
