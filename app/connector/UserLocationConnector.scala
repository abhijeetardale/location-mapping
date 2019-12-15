package connector

import config.AppConfig
import javax.inject.Inject
import play.api.libs.json.JsValue
import play.api.libs.ws.{WSClient, WSResponse}

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

class UserLocationConnector@Inject()(wsClient: WSClient, appConfig: AppConfig) {

  def getUsers: Future[JsValue] = {
    
    val serviceUrl = s"${appConfig.base}/users"

    wsClient.url(serviceUrl).get().map{
      _.json
    }

  }

}
