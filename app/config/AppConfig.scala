package config

import javax.inject.Inject
import play.api.{Configuration, Environment}

class AppConfig@Inject()(runModeConfiguration: Configuration, environment: Environment) {

  lazy val base: String= runModeConfiguration.get[String]("locationService.base")
  lazy val users: String= runModeConfiguration.get[String]("locationService.users")
  lazy val latitude: Double= runModeConfiguration.get[Double]("destination.latitude")
  lazy val longitude: Double= runModeConfiguration.get[Double]("destination.longitude")
  lazy val rangeInMile: Double= runModeConfiguration.get[Double]("destination.rangeInMile")
  lazy val earthRadiusInMiles: Double= runModeConfiguration.get[Double]("earthRadiusInMiles")

}
