import java.util.Scanner

/**
  * Created by jack on 7/17/16.
  */

object Postman {

  private val brain = new Brain()

  //sendMessage(new Response("Welcome to ChatBot."))
  //sendMessage("What is your name")

  /**
    * Print message generated to a user from a given response.
    * @param resp -> Response to give to user.
    */
  private def sendMessage(resp: Response) {
    OutputHandler.sendMessage(resp.getMessage)
  }

  private def sendMessage(resp: String) {
    OutputHandler.sendMessage(resp)
  }

  /**
    * Start a new chat sequence from a given 'Chat type'/
    * @param chatType ->
    */
  private def chat(message: String, chatType: Int) {
    chatType match {
      case ChatTypes.GET_NAME => {
        sendMessage(new Response("What is your name?"))
      }
      case ChatTypes.SET_INFO => {

      }
      case ChatTypes.GENERIC => {

      }
      case _ => {
        sendMessage(new Response("Chat Type is not yet supported."))
      }
    }
    newMessage(message, chatType)
  }

  def userMessage(message: String, chatType: Int): String = {
    newMessage(message, chatType)
  }

  /* newMessage(String message, Int chatType) collect response from brain
   * and then send it as a new message then chat again with appropriate type
   */
  def newMessage(message: String, chatType: Int): String = {
    try {
      val resp = brain.createResponse(message, chatType)
      resp.getMessage
    } catch {
      case e:Throwable => {
        "Sorry I could not understand what you said."
      }
    }
  }
}
