package models

import play.api.libs.functional.syntax._
import play.api.libs.json.Reads._
import play.api.libs.json.{JsPath, Json, OFormat, Reads}

case class User(
                 id:Int,
                 firstName:String,
                 lastName:String,
                 email: String,
                 ipAddress: String,
                 latitude:Double,
                 longitude:Double)

object User{
  implicit val format : OFormat[User] = Json.format

  val readDoubleFromString: Reads[Double] = implicitly[Reads[String]]
    .map(x => x.toDouble)

  val apiReads: Reads[User] = (
    (JsPath \ "id").read[Int] and
      (JsPath \ "first_name").read[String] and
      (JsPath \ "last_name").read[String] and
      (JsPath \ "email").read[String] and
      (JsPath \ "ip_address").read[String] and
      ((JsPath \ "latitude").read[Double] or (JsPath \ "latitude").read(readDoubleFromString)) and
      ((JsPath \ "longitude").read[Double] or (JsPath \ "longitude").read(readDoubleFromString))
    ) ((id, firstName, lastName, email, ip_address, latitude, longitude) =>
    User(id, firstName, lastName, email, ip_address, latitude, longitude))
}
