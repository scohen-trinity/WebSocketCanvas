package controllers

import javax.inject._

import play.api.mvc._
import play.api.i18n._
import models.MessageListInMemoryModel
import play.api.libs.json._
import models._
import scala.collection.mutable.ListBuffer

@Singleton
class Task11Controller @Inject() (cc: ControllerComponents) extends AbstractController(cc) { 
   
    private val drawingData: ListBuffer[ListBuffer[JsObject]] = ListBuffer.empty[ListBuffer[JsObject]]

    def receiveData(): Action[JsValue] = Action(parse.json) { request =>
        val receivedData = request.body.as[List[JsObject]]

        val convertedData: ListBuffer[JsObject] = ListBuffer(receivedData: _*)

        drawingData += convertedData

        Ok(Json.toJson(drawingData))
    }
}