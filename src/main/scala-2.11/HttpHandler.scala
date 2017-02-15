import colossus.core.{Initializer, ServerContext, WorkerRef}
import colossus.protocols.http.HttpMethod.Post
import colossus.protocols.http.{HttpHeader, HttpHeaders, HttpService}
import colossus.protocols.http.UrlParsing.{/, Root, on}
import colossus.service.Callback

class ChatService(context: ServerContext) extends HttpService(context) {

  val serverHeader = HttpHeader("Access-Control-Allow-Origin", "*")
  val serverHeaders = HttpHeaders(serverHeader)

  def handle = {
    case request @ Post on Root / "message" => {

      Callback.successful({
        //Postman.userMessage("Hi there")
        println(request.body.toString())
        val msgParams = StringUtil.parseMessageParams(request.body.toString())
        val chatType = DatabaseRPC.getChatTypeById(msgParams.id)
        // Get message and eventually get next chattype
        val msg = Postman.newMessage(msgParams.message, chatType)
        // TODO: Update chat type once we get it
        request.ok(msg, serverHeaders)
      })
    }
  }
}

class HttpHandler(worker: WorkerRef) extends Initializer(worker) {

  def onConnect = context => new ChatService(context)

}