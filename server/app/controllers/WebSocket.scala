package controllers

import actors.CanvasActor
import akka.actor.Props
import actors.CanvasManager
import javax.inject._
import akka.actor.Actor
import play.api.libs.streams.ActorFlow
import akka.actor.ActorSystem
import akka.stream.Materializer
import play.api.mvc._
import play.api.i18n._
import models.MessageListInMemoryModel
import play.api.libs.json._
import models._

@Singleton
class WebSocket @Inject() (cc: ControllerComponents) (implicit system: ActorSystem, mat: Materializer) extends AbstractController(cc) {
    val manager = system.actorOf(Props[CanvasManager], "Manager")
    def index = Action { implicit request =>
        Ok(views.html.index())
    }

    def socket = WebSocket.accept[String, String] { request =>
        ActorFlow.actorRef { out =>
            CanvasActor.props(out, manager)
        }
    }
}