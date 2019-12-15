package service

import models.User
import org.scalatest.MustMatchers
import org.scalatestplus.play.PlaySpec
import org.scalatestplus.play.guice.GuiceOneAppPerSuite
import play.api.test.Injecting

class LocationServiceSpec extends PlaySpec with GuiceOneAppPerSuite with MustMatchers with Injecting {

  val service = new LocationService()
  val user = User(1, "Maurise", "Shieldon", "mshieldon0@squidoo.com", "192.57.232.111", 34.003135, -117.7228641)
  "LocationService" when{

    "called with user coordinate" must{

     "return true if distance is within 50 miles" in {
       service.locate(user) mustBe true
     }

    }
  }
}
