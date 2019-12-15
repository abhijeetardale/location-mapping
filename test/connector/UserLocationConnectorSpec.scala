package connector

import com.github.tomakehurst.wiremock.client.WireMock.{equalTo, get, ok, stubFor, urlEqualTo}
import models.User
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
            ok(Json.parse("""{
                             |    "id": 1,
                             |    "first_name": "Maurise",
                             |    "last_name": "Shieldon",
                             |    "email": "mshieldon0@squidoo.com",
                             |    "ip_address": "192.57.232.111",
                             |    "latitude": 34.003135,
                             |    "longitude": -117.7228641
                             |  }""".stripMargin
            ).toString())
          )
        )

        val result = Await.result(inject[UserLocationConnector].getUsers, Duration.Inf)

        result mustBe Json.toJson(
          User(1, "Maurise", "Shieldon", "mshieldon0@squidoo.com", "192.57.232.111", 34.003135, -117.7228641)
        )
      }
    }
  }
}
