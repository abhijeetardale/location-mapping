package connector

import config.AppConfig
import javax.inject.Inject
import play.api.libs.json.JsValue
import play.api.http.Status._
import play.api.http.MimeTypes.JSON
import play.api.libs.ws.{WSClient, WSResponse}

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

class UserLocationConnector@Inject()(wsClient: WSClient, appConfig: AppConfig) {

  def getUsers: Future[Either[Throwable, JsValue]] = {
    
    val serviceUrl = s"${appConfig.base}/users"

    wsClient.url(serviceUrl).addHttpHeaders("Content-Type" -> JSON).get().map{
     response =>
        response.status match{
          case OK => Right(response.json)
          case _ => Left(throw new Exception(response.body))
        }
    }

  }

}
