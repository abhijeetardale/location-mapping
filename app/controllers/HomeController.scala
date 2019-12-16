package controllers

import config.AppConfig
import connector.UserLocationConnector
import javax.inject._
import play.api._
import play.api.mvc._
import service.LocationService

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

@Singleton
class HomeController @Inject()(
  val controllerComponents: ControllerComponents,
  userLocationConnector: UserLocationConnector,
  locationService: LocationService) extends BaseController {

  def index() = Action.async { implicit request: Request[AnyContent] =>

    userLocationConnector.getUsers.map{ response =>

      Ok(views.html.index(List.empty))

    }


  }
}
