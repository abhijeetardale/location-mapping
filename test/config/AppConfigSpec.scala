package config

import org.scalatest.MustMatchers
import org.scalatestplus.play.PlaySpec
import org.scalatestplus.play.guice.GuiceOneAppPerSuite
import play.api.{Configuration, Environment}
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
    }
  }
}
