package controllers

import cats.effect.Concurrent
import io.circe.Json
import io.circe.generic.auto._
import model.ClientControllerDTO.{AddClientRequest, ClientBalance, ClientStatus, ClientUpdateRequest, Payment, ScheduleDetailsRequest}
import org.http4s.{EntityDecoder, HttpRoutes}
import org.http4s.circe.CirceEntityEncoder._
import org.http4s.circe.jsonOf
import org.http4s.dsl.Http4sDsl
import services.client.ClientService
import cats.implicits._
import io.circe.syntax.EncoderOps
import org.http4s.circe._

class ClientController[F[_]: Concurrent](clientService: ClientService[F]) {
  private val dsl = Http4sDsl[F]

  import dsl._

  implicit val addClientRequestDecoder: EntityDecoder[F, AddClientRequest] = jsonOf[F, AddClientRequest]
  implicit val updateClientRequestDecoder: EntityDecoder[F, ClientUpdateRequest] = jsonOf[F, ClientUpdateRequest]
  implicit val paymentClientRequestDecoder: EntityDecoder[F, Payment] = jsonOf[F, Payment]
  implicit val scheduleDetailsClientRequestDecoder: EntityDecoder[F, ScheduleDetailsRequest] = jsonOf[F, ScheduleDetailsRequest]

  lazy val route: HttpRoutes[F] = HttpRoutes.of[F] {
    // Добавление клиента
    case req@POST -> Root / "clients" =>
      for {
        clientData <- req.as[AddClientRequest]
        response <- addClient(clientData).flatMap(Ok(_))
      } yield response

    // Изменение информации о клиенте
    case req@PUT -> Root / "clients" / clientId =>
      for {
        clientUpdateData <- req.as[ClientUpdateRequest]
        response <- updateClient(clientId, clientUpdateData).flatMap {
          case true => Ok("Client updated successfully")
          case false => NotFound("Client not found")
        }
      } yield response

    // Удаление клиента
    case DELETE -> Root / "clients" / clientId =>
      deleteClient(clientId).flatMap {
        case true => Ok("Client deleted successfully")
        case false => NotFound("Client not found")
      }

    // Предоставление информации о текущем статусе клиента
    case GET -> Root / "clients" / clientId / "status" =>
      getClientStatus(clientId).flatMap {
        case Some(status) => Ok(status.asJson)
        case None => NotFound("Client not found")
      }

    // Предоставление информации о балансе клиента
    case GET -> Root / "clients" / clientId / "balance" =>
      getClientBalance(clientId).flatMap {
        case Some(balance) => Ok(balance.asJson)
        case None => NotFound("Client not found")
      }

    // Добавление платежа
    case req@POST -> Root / "clients" / clientId / "payments" =>
      for {
        payment <- req.as[Payment]
        response <- addPayment(clientId, payment).flatMap(Ok(_))
      } yield response

    // Удаление платежа
    case DELETE -> Root / "clients" / clientId / "payments" / paymentId =>
      deletePayment(clientId, paymentId).flatMap {
        case true => Ok("Payment deleted successfully")
        case false => NotFound("Payment not found")
      }

    // Настройка автоматического списания
    case req@POST -> Root / "clients" / clientId / "schedule" =>
      for {
        scheduleDetails <- req.as[ScheduleDetailsRequest]
        response <- setupAutoDebit(clientId, scheduleDetails).flatMap(Ok(_))
      } yield response

    // Отмена автоматического списания
    case DELETE -> Root / "clients" / clientId / "schedule" / scheduleId =>
      cancelAutoDebit(clientId, scheduleId).flatMap {
        case true => Ok("Auto-debit canceled successfully")
        case false => NotFound("Schedule not found")
      }

    // Получение всех клиентов
    case GET -> Root / "clients" =>
      getClients.flatMap(Ok(_))

    // Получение всех платежей
    case GET -> Root / "payments" =>
      getPayments.flatMap(Ok(_))
  }

  // internal

  private def addClient(clientData: AddClientRequest): F[Json] = {
    for {
      result <- clientService.addClient(clientData)
    } yield result.asJson
  }

  private def updateClient(clientId: String, clientData: ClientUpdateRequest): F[Boolean] = {
    for {
      result <- clientService.updateClient(clientId, clientData)
    } yield result
  }

  private def deleteClient(clientId: String): F[Boolean] = {
    clientService.deleteClient(clientId)
  }

  private def getClientStatus(clientId: String): F[Option[ClientStatus]] = {
    clientService.getClientStatus(clientId)
  }

  private def getClientBalance(clientId: String): F[Option[ClientBalance]] = {
    clientService.getClientBalance(clientId)
  }

  private def addPayment(clientId: String, payment: Payment): F[Json] = {
    for {
      result <- clientService.addPayment(clientId, payment)
    } yield result.asJson
  }

  private def deletePayment(clientId: String, paymentId: String): F[Boolean] = {
    clientService.deletePayment(clientId, paymentId)
  }

  private def setupAutoDebit(clientId: String, scheduleDetails: ScheduleDetailsRequest): F[Json] = {
    for {
      result <- clientService.setupAutoDebit(clientId, scheduleDetails)
    } yield result.asJson
  }

  private def cancelAutoDebit(clientId: String, scheduleId: String): F[Boolean] = {
    clientService.cancelAutoDebit(clientId, scheduleId)
  }

  private def getClients: F[Json] = {
    for {
      result <- clientService.getClients
    } yield result.asJson
  }

  private def getPayments: F[Json] = {
    for {
      result <- clientService.getPayments
    } yield result.asJson
  }
}
