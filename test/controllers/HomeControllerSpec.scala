package controllers

import connector.UserLocationConnector
import org.scalatest.mockito.MockitoSugar
import org.mockito.Mockito._
import org.scalatestplus.play._
import org.scalatestplus.play.guice._
import play.api.test._
import play.api.test.Helpers._
import service.LocationService

import scala.concurrent.Future

class HomeControllerSpec extends PlaySpec with GuiceOneAppPerTest with Injecting with MockitoSugar {

  "HomeController GET" should {

    "render the index page without users and connector invoked" in {

      val mockUserLocationConnector = mock[UserLocationConnector]
      when(mockUserLocationConnector.getUsers).thenReturn(Future.successful(Right(List.empty)))
      when(mockUserLocationConnector.getUsers).thenReturn(Future.successful(Right(List.empty)))
      val controller = new HomeController(stubControllerComponents(), mockUserLocationConnector, inject[LocationService])
      val home = controller.index().apply(FakeRequest(GET, "/"))

      status(home) mustBe OK
      contentType(home) mustBe Some("text/html")
      contentAsString(home) must include ("Welcome to Play")
      verify(mockUserLocationConnector, times(1)).getUsers
    }
  }
}
