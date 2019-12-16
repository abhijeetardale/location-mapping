package controllers

import com.github.tomakehurst.wiremock.client.WireMock
import connector.UserLocationConnector
import exceptions.InternalServerException
import models.User
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

    "render the index page with users when connector returning result" in {

      val userLondon = User(1, "Maurise", "Shieldon", "mshieldon0@squidoo.com", "192.57.232.111", 51.50853, -0.12574)
      val userLondon25Mile = User(2, "Bendix", "Halgarth", "bhalgarth1@timesonline.co.u", "4.185.73.82", 51.803615, 0.137487)
      val userLondon50Mile = User(3, "Meghan", "Southall", "msouthall2@ihg.com", "21.243.184.21", 51.17894000, -1.07006000)

      when(mockUserLocationConnector.getUsers).thenReturn(Future.successful(Right(List(userLondon, userLondon25Mile, userLondon50Mile))))
      val controller = new HomeController(stubControllerComponents(), mockUserLocationConnector, inject[LocationService])
      val home = controller.index().apply(FakeRequest(GET, "/"))

      status(home) mustBe OK
      contentType(home) mustBe Some("text/html")
      contentAsString(home) must include ("Welcome to Play")
      contentAsString(home) must include ("Maurise")
      contentAsString(home) must include ("Bendix")
      contentAsString(home) must include ("Meghan")
      verify(mockUserLocationConnector, times(1)).getUsers
    }

    "redirect to error page if connector throwing the exception" in {
      when(mockUserLocationConnector.getUsers).thenReturn(Future.successful(Left(new InternalServerException("Error occured"))))
      val controller = new HomeController(stubControllerComponents(), mockUserLocationConnector, inject[LocationService])
      val home = controller.index().apply(FakeRequest(GET, "/"))

      status(home) mustBe SEE_OTHER
      redirectLocation(home) mustBe routes.ErrorController.error().url
      verify(mockUserLocationConnector, times(1)).getUsers
    }
  }
}
