import akka.actor.ActorSystem
import colossus.IOSystem
import colossus.core.Server



/**
  * Created by jack on 2017-02-12.
  */
object main extends App {

    implicit val actorSystem = ActorSystem()
    implicit val io = IOSystem()

    Server.start("chatbot", 9000){ worker => new HttpHandler(worker) }
    //private val s = new Scanner(System.in)

}