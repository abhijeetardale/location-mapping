package controllers

import javax.inject._
import play.api.mvc._

@Singleton
class ErrorController @Inject()(val controllerComponents: ControllerComponents) extends BaseController {

  def error() = Action { implicit request: Request[AnyContent] =>
    Ok(views.html.error())
  }
}
