import java.util.Scanner

/**
  * Created by jack on 7/17/16.
  */

class Postman {

  private val brain = new Brain()
  private val s = new Scanner(System.in)

  sendMessage(new Response("Welcome to ChatBot."))
  chat(ChatTypes.GET_NAME)

  /**
    * Print message generated to a user from a given response.
    * @param resp -> Response to give to user.
    */
  private def sendMessage(resp: Response) {
    println(resp.getMessage)
  }

  /**
    * Start a new chat sequence from a given 'Chat type'/
    * @param chatType ->
    */
  private def chat(chatType: Int) {
    chatType match {
      case ChatTypes.GET_NAME => {
        sendMessage(new Response("What is your name?"))
      }
      case ChatTypes.SET_INFO => {

      }
      case _ => {
        sendMessage(new Response("Chat Type is not yet supported."))
      }
    }
    val userMessage = s.nextLine
    newMessage(userMessage, chatType)
  }


  def newMessage(message: String, chatType: Int) {
    val resp = brain.createResponse(message, chatType)
    sendMessage(resp)
    // Call chat with a new cht type here.....also add a field in response object
    // that indicates whether user asked a question
    chat(resp.nextChatType)
  }
}
