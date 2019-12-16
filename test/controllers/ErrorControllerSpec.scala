package controllers

import org.scalatestplus.play._
import org.scalatestplus.play.guice._
import play.api.test._
import play.api.test.Helpers._

class ErrorControllerSpec extends PlaySpec with GuiceOneAppPerTest with Injecting {

  "ErrorController GET" should {

    "render the error page from a new instance of controller" in {
      val controller = new ErrorController(stubControllerComponents())
      val error = controller.error().apply(FakeRequest(GET, "/"))

      status(error) mustBe OK
      contentType(error) mustBe Some("text/html")
      contentAsString(error) must include ("Something has gone wrong !!!")
    }

    "render the error page from the application" in {
      val controller = inject[ErrorController]
      val error = controller.error().apply(FakeRequest(GET, "/"))

      status(error) mustBe OK
      contentType(error) mustBe Some("text/html")
      contentAsString(error) must include ("Something has gone wrong !!!")
    }

    "render the error page from the router" in {
      val request = FakeRequest(GET, "/error")
      val error = route(app, request).get

      status(error) mustBe OK
      contentType(error) mustBe Some("text/html")
      contentAsString(error) must include ("Something has gone wrong !!!")
    }
  }
}
