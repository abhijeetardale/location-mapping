package connector

import config.AppConfig
import exceptions._
import javax.inject.Inject
import models.User
import play.api.http.MimeTypes.JSON
import play.api.http.Status._
import play.api.libs.json.JsResultException
import play.api.libs.ws.WSClient
import play.api.libs.json.Reads.seq

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

class UserLocationConnector@Inject()(wsClient: WSClient, appConfig: AppConfig) {

  def getUsers: Future[Either[Exception, Seq[User]]] = {
    
    val serviceUrl = s"${appConfig.base}${appConfig.users}"

    wsClient.url(serviceUrl).addHttpHeaders("Content-Type" -> JSON).get().map{
     response =>
        response.status match{
          case OK => response.json.validate[Seq[User]](seq(User.apiReads)).fold(
            error => Left(throw new BadRequestException(
              s"$getUsers to $serviceUrl returned invalid JSON" +
                JsResultException(error).getMessage
            )),
            valid => Right(valid)
          )
          case BAD_REQUEST => Left(throw new BadRequestException(response.body))
          case NOT_FOUND => Left(throw new NotFoundException(response.body))
          case INTERNAL_SERVER_ERROR => Left(throw new InternalServerException(response.body))
          case NOT_IMPLEMENTED => Left(throw new NotImplementedException(response.body))
          case BAD_GATEWAY => Left(throw new BadGatewayException(response.body))
          case SERVICE_UNAVAILABLE => Left(throw new ServiceUnavailableException(response.body))
          case _ => Left(throw new UnrecognisedHttpResponseException("getUsers", serviceUrl, response))
        }
    }

  }

}
