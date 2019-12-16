package controllers

import connector.UserLocationConnector
import javax.inject._
import play.api.mvc._
import service.LocationService

import scala.concurrent.ExecutionContext.Implicits.global

@Singleton
class HomeController @Inject()(
  val controllerComponents: ControllerComponents,
  userLocationConnector: UserLocationConnector,
  locationService: LocationService) extends BaseController {

  def index() = Action.async { implicit request: Request[AnyContent] =>

    userLocationConnector.getUsers.map{ response =>
      response.fold(
        _ =>  Redirect(routes.ErrorController.error()),
        results => {
          Ok(views.html.index(locationService.filterUser(results)))
        }
      )
    }


  }
}
