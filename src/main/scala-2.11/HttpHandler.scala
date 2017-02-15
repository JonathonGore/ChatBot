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
        // Parse UUID and Message from the request
        val msgParams = StringUtil.parseMessageParams(request.body.toString())
        // Get the chat Type we are going to chat with
        val chatType = DatabaseRPC.getChatTypeById(msgParams.id)
        // create new Chatbot Response from the chat type
        val response = Postman.newMessage(msgParams.message, chatType)
        // Update the chat type for this particular user (UUID)
        DatabaseRPC.updateChatTypeById(msgParams.id, response.nextChatType)
        // Respond with the create message
        request.ok(response.getMessage, serverHeaders)
      })
    }
  }
}

class HttpHandler(worker: WorkerRef) extends Initializer(worker) {

  def onConnect = context => new ChatService(context)

}