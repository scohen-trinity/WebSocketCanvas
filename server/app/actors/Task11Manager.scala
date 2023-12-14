package actors

import akka.actor.Actor
import akka.actor.ActorRef

class CanvasManager extends Actor {
    private var artists = List.empty[ActorRef]
    private var lineList: Set[(String, List[(Int, Int)])] = Set.empty;

    import CanvasManager._
    
    def receive = {
        case NewArtist(artist) => 
            artists ::= artist
        
        case DrawingData(data) =>
            data.foreach( line =>
                lineList += line    
            )
            // println(lineList)
            artists.foreach(_ ! lineList)
            // lineList.foreach( line => 
            //     artists.foreach(_ ! line)   
            // )

        case m => println("Unhandled message in CanvasManager: " + m)
    }
}

object CanvasManager {
    case class NewArtist(artist: ActorRef)
    case class DrawingData(data: List[(String, List[(Int, Int)])])
}