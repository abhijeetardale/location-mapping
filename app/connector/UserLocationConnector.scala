package connector

import config.AppConfig
import javax.inject.Inject
import play.api.libs.ws.{WSClient, WSResponse}

import scala.concurrent.Future

class UserLocationConnector@Inject()(wsClient: WSClient, appConfig: AppConfig) {

  def getUsers: Future[WSResponse] = {
    
    val serviceUrl = s"${appConfig.base}/users"

    wsClient.url(serviceUrl).get()

  }

}
