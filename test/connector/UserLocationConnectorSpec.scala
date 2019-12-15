package connector

import com.github.tomakehurst.wiremock.client.WireMock._
import exceptions._
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
            ok(Json.parse(
              """[{
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


      "throw BadRequestException if status is 400" in {

        stubFor(get(urlEqualTo(path))
          .withHeader("Content-Type", equalTo("application/json"))
          .willReturn(
            badRequest().withBody("Bad Request")
          )
        )

        intercept[BadRequestException](Await.result(inject[UserLocationConnector].getUsers, Duration.Inf))
          .getMessage mustBe "Bad Request"

      }


      "throw NotFoundException if status is 404" in {

        stubFor(get(urlEqualTo(path))
          .withHeader("Content-Type", equalTo("application/json"))
          .willReturn(
            notFound().withBody("Not Found")
          )
        )

        intercept[NotFoundException](Await.result(inject[UserLocationConnector].getUsers, Duration.Inf))
          .getMessage mustBe "Not Found"

      }


      "throw InternalServerException if status is not 500" in {

        stubFor(get(urlEqualTo(path))
          .withHeader("Content-Type", equalTo("application/json"))
          .willReturn(
            serverError().withBody("Internal Server")
          )
        )

        intercept[InternalServerException](Await.result(inject[UserLocationConnector].getUsers, Duration.Inf))
          .getMessage mustBe "Internal Server"

      }


      "throw NotImplementedException if status is not 501" in {

        stubFor(get(urlEqualTo(path))
          .withHeader("Content-Type", equalTo("application/json"))
          .willReturn(
            aResponse().withStatus(501).withBody("Not Implemented")
          )
        )

        intercept[NotImplementedException](Await.result(inject[UserLocationConnector].getUsers, Duration.Inf))
          .getMessage mustBe "Not Implemented"

      }


      "throw BadGatewayException if status is not 502" in {

        stubFor(get(urlEqualTo(path))
          .withHeader("Content-Type", equalTo("application/json"))
          .willReturn(
            aResponse().withStatus(502).withBody("Bad Gateway")
          )
        )

        intercept[BadGatewayException](Await.result(inject[UserLocationConnector].getUsers, Duration.Inf))
          .getMessage mustBe "Bad Gateway"

      }


      "throw ServiceUnavailableException if status is not 503" in {

        stubFor(get(urlEqualTo(path))
          .withHeader("Content-Type", equalTo("application/json"))
          .willReturn(
            aResponse().withStatus(503).withBody("Service Unavailable")
          )
        )

        intercept[ServiceUnavailableException](Await.result(inject[UserLocationConnector].getUsers, Duration.Inf))
          .getMessage mustBe "Service Unavailable"

      }

      "throw UnrecognisedHttpResponseException for all other http response code" in {

        stubFor(get(urlEqualTo(path))
          .withHeader("Content-Type", equalTo("application/json"))
          .willReturn(
              aResponse().withStatus(504)
            )
        )

        intercept[UnrecognisedHttpResponseException](Await.result(inject[UserLocationConnector].getUsers, Duration.Inf))
          .getMessage must include("failed with status 504")
      }
    }
  }
}
