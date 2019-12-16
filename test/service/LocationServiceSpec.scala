package service

import models.User
import org.scalatest.MustMatchers
import org.scalatestplus.play.PlaySpec
import org.scalatestplus.play.guice.GuiceOneAppPerSuite
import play.api.test.Injecting

class LocationServiceSpec extends PlaySpec with GuiceOneAppPerSuite with MustMatchers with Injecting {

  val service = new LocationService()
  val userLondon = User(1, "Maurise", "Shieldon", "mshieldon0@squidoo.com", "192.57.232.111", 51.50853, -0.12574)
  val userLondon25Mile = User(2, "Bendix", "Halgarth", "bhalgarth1@timesonline.co.u", "4.185.73.82", 51.803615, 0.137487)
  val userLondon50Mile = User(3, "Meghan", "Southall", "msouthall2@ihg.com", "21.243.184.21", 51.17894000, -1.07006000)
  val userLondonMoreThan50Mile = User(4, "Sidnee", "Silwood", "ssilwood3@gizmodo.com", "77.55.231.220", 52.192001, -2.220000)

  "LocationService" when{

    "called with user coordinate" must{

     "return false if distance is 0 mile away from London" in {
       service.locate(userLondon) mustBe false
     }

     "return false if distance is 25 mile away from London" in {
       service.locate(userLondon25Mile) mustBe false
     }

     "return false if distance is exact 50 miles away from London" in {
       service.locate(userLondon50Mile) mustBe false
     }

     "return true if distance is more than 50 miles away from London" in {
       service.locate(userLondonMoreThan50Mile) mustBe true
     }

    }
  }

  "called with list of user" must{

    "return empty list when all the address are wthinin the range" in {

      service.filterUser(List(userLondon, userLondon25Mile, userLondon50Mile)) mustBe List.empty
    }

    "return list of users when all the address are not in range" in {

      service.filterUser(List(userLondon, userLondon25Mile, userLondon50Mile, userLondonMoreThan50Mile)) mustBe List(userLondonMoreThan50Mile)
    }

  }
}
