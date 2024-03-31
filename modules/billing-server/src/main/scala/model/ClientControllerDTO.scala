package model

import io.swagger.annotations.{ApiModel, ApiModelProperty}
import java.time.LocalDate

import scala.annotation.meta.field

object ClientControllerDTO {

  @ApiModel(description = "Информация для создания клиента")
  case class AddClientRequest(
    @(ApiModelProperty @field)(value = "Имя клиента", required = true) name: String,
    @(ApiModelProperty @field)(value = "Электронная почта клиента", required = true) email: String,
    @(ApiModelProperty @field)(value = "Телефон клиента", required = true) phone: String
  )

  @ApiModel(description = "Ответ на добавление клиента")
  case class ClientResponse(
    @(ApiModelProperty @field)(value = "Уникальный идентификатор клиента в системе", required = true) clientId: String,
    @(ApiModelProperty @field)(value = "Текущий баланс клиента после добавления", required = true) currentBalance: ClientBalance,
    @(ApiModelProperty @field)(value = "Статус клиента после добавления (Подключение, Активен, Блокировка, Расторгнут)", required = true) status: ClientStatus,
    @(ApiModelProperty @field)(value = "Имя клиента", required = false) name: Option[String] = None,
    @(ApiModelProperty @field)(value = "Электронная почта клиента", required = false) email: Option[String] = None,
    @(ApiModelProperty @field)(value = "Адрес клиента", required = false) address: Option[String] = None,
    @(ApiModelProperty @field)(value = "Телефон клиента", required = false) phone: Option[String] = None
  )

  @ApiModel(description = "Информация для обновления клиента")
  case class ClientUpdateRequest(
   @(ApiModelProperty @field)(value = "Электронная почта клиента", required = false) email: Option[String] = None,
   @(ApiModelProperty @field)(value = "Телефон клиента", required = false) phone: Option[String] = None,
   @(ApiModelProperty @field)(value = "Статус клиента", required = false) clientStatus: Option[ClientStatus] = None
  )

  @ApiModel(description = "Информация для обновления подписок клиента")
  case class UpdateClientServiceRequest(
   @(ApiModelProperty @field)(value = "Идентификатор клиента", required = true) clientId: String,
   @(ApiModelProperty @field)(value = "Идентификаторы услуг", required = true) serviceIds: Seq[String]
  )

  @ApiModel(description = "Информация о балансе клиента")
  case class ClientBalance(
    @(ApiModelProperty @field)(value = "Баланс клиента", required = true) amount: BigDecimal,
    @(ApiModelProperty @field)(value = "Лимит клиента", required = true) limit: BigDecimal
  )

  @ApiModel(description = "Статус клиента")
  sealed trait ClientStatus
  object ClientStatus {
    case object Connected extends ClientStatus
    case object Active extends ClientStatus
    case object Blocked extends ClientStatus
    case object Terminated extends ClientStatus

    case object Undefined extends ClientStatus

    import io.circe.{Decoder, Encoder, HCursor, Json}
    import io.circe.syntax._

    implicit val clientStatusEncoder: Encoder[ClientStatus] = new Encoder[ClientStatus] {
      override def apply(a: ClientStatus): Json = a match {
        case ClientStatus.Connected => "Connected".asJson
        case ClientStatus.Active => "Active".asJson
        case ClientStatus.Blocked => "Blocked".asJson
        case ClientStatus.Terminated => "Terminated".asJson
        case ClientStatus.Undefined => "Undefined".asJson
      }
    }

    implicit val clientStatusDecoder: Decoder[ClientStatus] = new Decoder[ClientStatus] {
      override def apply(c: HCursor): Decoder.Result[ClientStatus] =
        c.as[String].map {
          case "Connected" => ClientStatus.Connected
          case "Active" => ClientStatus.Active
          case "Blocked" => ClientStatus.Blocked
          case "Terminated" => ClientStatus.Terminated
        }
    }

    def fromString(str: String): Option[ClientStatus] = str match {
      case "Connected" => Some(ClientStatus.Connected)
      case "Active" => Some(ClientStatus.Active)
      case "Blocked" => Some(ClientStatus.Blocked)
      case "Terminated" => Some(ClientStatus.Terminated)
      case _ => Some(ClientStatus.Undefined)
    }

  }

  @ApiModel(description = "Платеж клиента")
  case class Payment(
    @(ApiModelProperty @field)(value = "Идентификатор платежа", required = false) id: Option[String],
    @(ApiModelProperty @field)(value = "Уникальный идентификатор клиента в системе", required = true) clientId: String,
    @(ApiModelProperty @field)(value = "Сумма платежа", required = true) amount: BigDecimal,
    @(ApiModelProperty @field)(value = "Дата платежа", required = true) date: LocalDate,
    @(ApiModelProperty @field)(value = "Способ платежа", required = true) method: String,
    @(ApiModelProperty @field)(value = "Описание платежа", required = false) description: Option[String] = None,
    @(ApiModelProperty @field)(value = "Идентификатор транзакции", required = false) transactionId: Option[String] = None
  )

  @ApiModel(description = "Детали настройки автоматического списания")
  case class ScheduleDetailsRequest(
    @(ApiModelProperty @field)(value = "Дата начала списания", required = true) startDate: LocalDate,
    @(ApiModelProperty @field)(value = "Периодичность списания", required = true) frequency: String,
    @(ApiModelProperty @field)(value = "Сумма списания", required = true) amount: Double,
    @(ApiModelProperty @field)(value = "Название услуги", required = false) serviceName: Option[String] = None,
    @(ApiModelProperty @field)(value = "Примечание к платежу", required = false) note: Option[String] = None
  )

  @ApiModel(description = "Ответ на настройку автоматического списания")
  case class ScheduleDetailsResponse(
   @(ApiModelProperty @field)(value = "Дата начала списания", required = true) startDate: LocalDate,
   @(ApiModelProperty @field)(value = "Периодичность списания", required = true) frequency: String,
   @(ApiModelProperty @field)(value = "Сумма списания", required = true) amount: Double,
   @(ApiModelProperty @field)(value = "Название услуги", required = false) serviceName: Option[String],
   @(ApiModelProperty @field)(value = "Примечание к платежу", required = false) note: Option[String],
   @(ApiModelProperty @field)(value = "Статус настройки автоматического списания", required = true) status: String,
   @(ApiModelProperty @field)(value = "Дата и время создания настройки", required = true) createdAt: String
 )

}
