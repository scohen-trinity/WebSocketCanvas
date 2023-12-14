package actors

import akka.actor.Actor
import akka.actor.Props
import akka.actor.ActorRef
import play.api.libs.json._

class CanvasActor(out: ActorRef, manager: ActorRef) extends Actor {
    manager ! CanvasManager.NewArtist(self)
    
    import CanvasActor._

    var drawingData: Set[(String, List[(Int, Int)])] = Set.empty

    def receive: Receive = {
        case Draw(x, y) =>
            println("Drawing")

        case jsonString: String => 
            val jsonValue: JsValue = Json.parse(jsonString)
            val color = jsonValue(0).as[String]
            val line = jsonValue(1).as[List[(Int, Int)]]

            val colored_line = (color, line)

            drawingData = drawingData + colored_line
            
            manager ! CanvasManager.DrawingData(drawingData.toList)

        case data: Set[(String, List[(Int, Int)])] => 
            val jsonData = Json.toJson(data).toString()
        
            out ! jsonData

        case m => println(s"Unhandled error in actor, $m")
    }

    out ! "Connected"
}

object CanvasActor {
    def props(out: ActorRef, manager: ActorRef) = Props(new CanvasActor(out, manager))

    case class Draw(x:Int, y: Int)
}