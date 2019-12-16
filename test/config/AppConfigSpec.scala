package config

import org.scalatest.MustMatchers
import org.scalatestplus.play.PlaySpec
import org.scalatestplus.play.guice.GuiceOneAppPerSuite
import play.api.inject.guice.GuiceApplicationBuilder
import play.api.{Application, Configuration, Environment}
import play.api.test.Injecting

class AppConfigSpec extends PlaySpec with GuiceOneAppPerSuite with MustMatchers with Injecting {

  private val config = inject[Configuration]
  private val environment = inject[Environment]

  val appConfig = new AppConfig(config, environment)

  "AppConfig" when {

    "called" must {

      "return the configured base url" in {

        appConfig.base mustBe "https://bpdts-test-app.herokuapp.com"
      }

      "return the mock base url" in {

        val app: Application = new GuiceApplicationBuilder().configure(
          "locationService.base" -> s"http://localhost:8080").build()
        val config = app.injector.instanceOf[Configuration]
        val environment = app.injector.instanceOf[Environment]

        val appConfig = new AppConfig(config, environment)
        appConfig.base mustBe "http://localhost:8080"
      }

      "return the configured users url" in {

        appConfig.users mustBe "/users"
      }

      "return the configured latitude" in {

        appConfig.latitude mustBe 51.50853
      }

      "return the configured longitude" in {

        appConfig.longitude mustBe -0.12574
      }

      "return the configured earth radius in miles" in {

        appConfig.earthRadiusInMiles mustBe 3959.87433
      }
    }
  }
}
