package connector

import com.github.tomakehurst.wiremock.client.WireMock.{equalTo, get, ok, stubFor, urlEqualTo}
import org.scalatest.{EitherValues, OptionValues, RecoverMethods}
import play.api.libs.json.Json
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
            ok(Json.toJson("""{ [ "id" : "1" ]}""").toString())
          )
        )

        val result = Await.result(inject[UserLocationConnector].getUsers, Duration.Inf)

        result mustBe Json.toJson("""{ [ "id" : "1" ]}""")
      }
    }
  }
}
