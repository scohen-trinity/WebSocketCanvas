package models
import play.api.libs.json._

case class UserData(username: String, password: String)

object UserData {
    implicit val userDataReads: Reads[UserData] = Json.reads[UserData]
}