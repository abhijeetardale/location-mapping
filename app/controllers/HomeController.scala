package controllers

import config.AppConfig
import connector.UserLocationConnector
import javax.inject._
import play.api._
import play.api.mvc._
import service.LocationService

@Singleton
class HomeController @Inject()(
  val controllerComponents: ControllerComponents,
  userLocationConnector: UserLocationConnector,
  locationService: LocationService) extends BaseController {

  def index() = Action { implicit request: Request[AnyContent] =>

    Ok(views.html.index(List.empty))
  }
}
