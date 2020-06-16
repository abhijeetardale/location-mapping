package service

import config.AppConfig
import models.User
import org.scalatest.MustMatchers
import org.scalatestplus.play.PlaySpec
import org.scalatestplus.play.guice.GuiceOneAppPerSuite
import play.api.test.Injecting

class LocationServiceSpec extends PlaySpec with GuiceOneAppPerSuite with MustMatchers with Injecting {

  val service = new LocationService(inject[AppConfig])

  val userLondon = User(1, "Maurise", "Shieldon", "mshieldon0@squidoo.com", "192.57.232.111", 51.50853, -0.12574)
  val userLondon25Mile = User(2, "Bendix", "Halgarth", "bhalgarth1@timesonline.co.u", "4.185.73.82", 51.803615, 0.137487)
  val userLondon50Mile = User(3, "Meghan", "Southall", "msouthall2@ihg.com", "21.243.184.21", 51.17894000, -1.07006000)
  val userLondonMoreThan50Mile = User(4, "Sidnee", "Silwood", "ssilwood3@gizmodo.com", "77.55.231.220", 52.192001, -2.220000)


//    [
//      {
//        "id": 135,
//        "first_name": "Mechelle",
//        "last_name": "Boam",
//        "email": "mboam3q@thetimes.co.uk",
//        "ip_address": "113.71.242.187",
//        "latitude": -6.5115909,
//        "longitude": 105.652983
//      },
//      {
//        "id": 396,
//        "first_name": "Terry",
//        "last_name": "Stowgill",
//        "email": "tstowgillaz@webeden.co.uk",
//        "ip_address": "143.190.50.240",
//        "latitude": -6.7098551,
//        "longitude": 111.3479498
//      },
//      {
//        "id": 520,
//        "first_name": "Andrew",
//        "last_name": "Seabrocke",
//        "email": "aseabrockeef@indiegogo.com",
//        "ip_address": "28.146.197.176",
//        "latitude": "27.69417",
//        "longitude": "109.73583"
//      },
//      {
//        "id": 658,
//        "first_name": "Stephen",
//        "last_name": "Mapstone",
//        "email": "smapstonei9@bandcamp.com",
//        "ip_address": "187.79.141.124",
//        "latitude": -8.1844859,
//        "longitude": 113.6680747
//      },
//      {
//        "id": 688,
//        "first_name": "Tiffi",
//        "last_name": "Colbertson",
//        "email": "tcolbertsonj3@vimeo.com",
//        "ip_address": "141.49.93.0",
//        "latitude": 37.13,
//        "longitude": -84.08
//      },
//      {
//        "id": 794,
//        "first_name": "Katee",
//        "last_name": "Gopsall",
//        "email": "kgopsallm1@cam.ac.uk",
//        "ip_address": "203.138.133.164",
//        "latitude": 5.7204203,
//        "longitude": 10.901604
//      }
//    ]

  "LocationService" when{

    "called with user coordinate" must{

     "return true if distance is 0 mile away from London" in {
       service.locate(userLondon) mustBe true
     }

     "return true if distance is 25 mile away from London" in {
       service.locate(userLondon25Mile) mustBe true
     }

     "return true if distance is exact 50 miles away from London" in {
       service.locate(userLondon50Mile) mustBe true
     }

     "return false if distance is more than 50 miles away from London" in {
       service.locate(userLondonMoreThan50Mile) mustBe false
     }

    }
  }

  "called with list of user" must{

    "return user list when all the address are wthin the range" in {

      service.filterUser(List(userLondon, userLondon25Mile, userLondon50Mile)) mustBe List(
        userLondon, userLondon25Mile, userLondon50Mile)
    }

    "return list of users excluding the address which is not in range" in {

      service.filterUser(List(userLondon, userLondon25Mile, userLondon50Mile, userLondonMoreThan50Mile, userLondonMoreThan50Mile)) mustBe List(
        userLondon, userLondon25Mile, userLondon50Mile)
    }

    "return empty user list when the address which is not in range" in {

      service.filterUser(List(userLondonMoreThan50Mile)) mustBe List.empty
    }

  }
}
