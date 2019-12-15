package connector

import com.github.tomakehurst.wiremock.client.WireMock._
import org.scalatest.{EitherValues, OptionValues, RecoverMethods}
import testutils.WireMockServerHelper

import scala.concurrent.Await
import scala.concurrent.duration.Duration

class UserLocationConnectorSpec extends WireMockServerHelper
  with OptionValues
  with RecoverMethods
  with EitherValues {

  val path = "/users"

  "UserLocationConnector" when {

    "called for user list defined by DWP" must {

      "return 200" in {
        stubFor(get(urlEqualTo(path))
          .willReturn(
            ok()
          )
        )

        val result = Await.result(inject[UserLocationConnector].getUsers, Duration.Inf)

        result.status mustBe 200
      }
    }
  }
}
