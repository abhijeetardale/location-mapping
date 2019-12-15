package config

import org.scalatest.MustMatchers
import org.scalatestplus.play.PlaySpec
import org.scalatestplus.play.guice.GuiceOneAppPerSuite
import play.api.test.Injecting

class AppConfigSpec extends PlaySpec with GuiceOneAppPerSuite with MustMatchers with Injecting {

  "AppConfig" when {

    "called" must {

      "return the configured base url" in {

        AppConfig.base mustBe "https://bpdts-test-app.herokuapp.com"

      }
    }
  }
}
