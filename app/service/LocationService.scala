package service

import config.AppConfig
import models.User

//The haversine formula determines the great-circle distance between two points on a sphere given their
// longitudes and latitudes. Important in navigation, it is a special case of a more general
// formula in spherical trigonometry, the law of haversines, that relates the sides and angles of
// spherical triangles.
//R = earth’s radius
//Δlat = lat2− lat1; Δlong = long2− long1
//a = sin²(Δlat/2) + cos(lat1) * cos(lat2) * sin²(Δlong/2)
//c = 2*atan2(√a, √(1−a)); d = R*c

class LocationService(appConfig: AppConfig) {

  def locate(user: User): Boolean = {

    val latitudeDistance = Math.toRadians(user.latitude - appConfig.latitude)
    val longitudeDistance = Math.toRadians(user.longitude - appConfig.longitude)

    val sinLat = Math.sin(latitudeDistance / 2)
    val sinLng = Math.sin(longitudeDistance / 2)

    val a = sinLat * sinLat +
      (Math.cos(Math.toRadians(user.latitude))
        * Math.cos(Math.toRadians(appConfig.latitude))
        * sinLng * sinLng)

    val c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a))

    (appConfig.earthRadiusInMiles * c).round >= appConfig.rangeInMile
  }


  def filterUser(users: List[User]): List[User] = {
    users.filter(locate)
  }

}
