package connector

import com.github.tomakehurst.wiremock.client.WireMock.{badRequest, equalTo, get, ok, stubFor, urlEqualTo}
import exceptions.BadRequestException
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

      "return user list when 200 and for mime tye application json" in {
        stubFor(get(urlEqualTo(path))
          .withHeader("Content-Type", equalTo("application/json"))
          .willReturn(
            ok(Json.parse("""[{
                             |    "id": 1,
                             |    "first_name": "Maurise",
                             |    "last_name": "Shieldon",
                             |    "email": "mshieldon0@squidoo.com",
                             |    "ip_address": "192.57.232.111",
                             |    "latitude": 34.003135,
                             |    "longitude": -117.7228641
                             |  }]""".stripMargin
            ).toString())
              .withHeader("Content-Type", "application/json")
          )
        )

        val result = Await.result(inject[UserLocationConnector].getUsers, Duration.Inf)

        result mustBe Right(Json.toJson(List(
          User(1, "Maurise", "Shieldon", "mshieldon0@squidoo.com", "192.57.232.111", 34.003135, -117.7228641)))
        )
      }


      "throw exception if status is not 200" in {

        stubFor(get(urlEqualTo(path))
          .withHeader("Content-Type", equalTo("application/json"))
          .willReturn(
            badRequest().withBody("Bad Request")
          )
        )

        intercept[BadRequestException](Await.result(inject[UserLocationConnector].getUsers, Duration.Inf))
          .getMessage mustBe "Bad Request"

      }

    }
  }
}
