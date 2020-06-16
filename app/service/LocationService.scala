package service

import config.AppConfig
import javax.inject.Inject
import models.User
class LocationService@Inject()(appConfig: AppConfig) {

  /*The haversine formula determines the great-circle distance between two points on a sphere given their
   longitudes and latitudes. Important in navigation, it is a special case of a more general
   formula in spherical trigonometry, the law of haversines, that relates the sides and angles of
   spherical triangles.
  R = earth’s radius
  tlat = lat2− lat1; tlong = long2− long1
  a = sin²(tlat/2) + cos(lat1) * cos(lat2) * sin²(tlong/2)
  c = 2*atan2(√a, √(1−a)); d = R*c*/

//  def locate(user: User): Boolean = {
//
//    val latitudeDistance = Math.toRadians(user.latitude - appConfig.latitude)
//    val longitudeDistance = Math.toRadians(user.longitude - appConfig.longitude)
//
//    val sinLat = Math.sin(latitudeDistance / 2)
//    val sinLng = Math.sin(longitudeDistance / 2)
//
//    val a = sinLat * sinLat +
//      (Math.cos(Math.toRadians(user.latitude))
//        * Math.cos(Math.toRadians(appConfig.latitude))
//        * sinLng * sinLng)
//
//    val c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a))
//
//    (appConfig.earthRadiusInMiles * c).round <= appConfig.rangeInMile
//  }


  // new implementation using below reference
  // https://www.movable-type.co.uk/scripts/latlong.html

  def locate(user: User): Boolean = {

    val lat1 = user.latitude
    val lon1 = user.longitude
    val lat2 = appConfig.latitude
    val lon2 = appConfig.longitude

    val R = 6371e3 // metres
    val q1 = lat1 * Math.PI/180 // q, y in radians
    val q2 = lat2 * Math.PI/180
    val tq = (lat2-lat1) * Math.PI/180
    val ty = (lon2-lon1) * Math.PI/180

    val a1 = Math.sin(tq/2) * Math.sin(tq/2) +
      Math.cos(q1) * Math.cos(q2) *
        Math.sin(ty/2) * Math.sin(ty/2)

    val c1 = 2 * Math.atan2(Math.sqrt(a1), Math.sqrt(1-a1))

    val d = R * c1 * 0.0006213712 // in miles

    d <= appConfig.rangeInMile

  }


  def filterUser(users: Seq[User]): List[User] = {
    users.filter(locate).toList
  }

}
