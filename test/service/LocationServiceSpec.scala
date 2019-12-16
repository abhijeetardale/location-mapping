package service

import models.User
import org.scalatest.MustMatchers
import org.scalatestplus.play.PlaySpec
import org.scalatestplus.play.guice.GuiceOneAppPerSuite
import play.api.test.Injecting

class LocationServiceSpec extends PlaySpec with GuiceOneAppPerSuite with MustMatchers with Injecting {

  val service = new LocationService()
  val userLondon = User(1, "Maurise", "Shieldon", "mshieldon0@squidoo.com", "192.57.232.111", 51.50853, -0.12574)

  "LocationService" when{

    "called with user coordinate" must{

     "return true if distance is 0 mile away from London" in {
       service.locate(userLondon) mustBe true
     }

    }
  }
}
