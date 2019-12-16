package controllers

import com.github.tomakehurst.wiremock.client.WireMock
import connector.UserLocationConnector
import exceptions.InternalServerException
import org.scalatest.mockito.MockitoSugar
import org.mockito.Mockito._
import org.scalatest.{BeforeAndAfter, BeforeAndAfterEach}
import org.scalatestplus.play._
import org.scalatestplus.play.guice._
import play.api.test._
import play.api.test.Helpers._
import service.LocationService

import scala.concurrent.Future

class HomeControllerSpec extends PlaySpec with GuiceOneAppPerTest with Injecting with MockitoSugar with BeforeAndAfterEach {

  override def beforeEach(): Unit = reset(mockUserLocationConnector)

  val mockUserLocationConnector = mock[UserLocationConnector]
  
  "HomeController GET" should {

    "render the index page without users and connector invoked" in {

      when(mockUserLocationConnector.getUsers).thenReturn(Future.successful(Right(List.empty)))
      val controller = new HomeController(stubControllerComponents(), mockUserLocationConnector, inject[LocationService])
      val home = controller.index().apply(FakeRequest(GET, "/"))

      status(home) mustBe OK
      contentType(home) mustBe Some("text/html")
      contentAsString(home) must include ("Welcome to Play")
      verify(mockUserLocationConnector, times(1)).getUsers
    }

    "redirect to error page if connector throwing the exception" in {
      when(mockUserLocationConnector.getUsers).thenReturn(Future.successful(Left(new InternalServerException("Error occured"))))
      val controller = new HomeController(stubControllerComponents(), mockUserLocationConnector, inject[LocationService])
      val home = controller.index().apply(FakeRequest(GET, "/"))

      status(home) mustBe SEE_OTHER
      verify(mockUserLocationConnector, times(1)).getUsers
    }
  }
}
