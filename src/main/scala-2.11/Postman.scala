import java.util.Scanner

/**
  * Created by jack on 7/17/16.
  */

object Postman {

  private val brain = new Brain()

  /* newMessage(String message, Int chatType) collect response from brain
   * and then send it as a new message then chat again with appropriate type
   */
  def newMessage(message: String, chatType: Int): Response = {
    try {
      brain.createResponse(message, chatType)
    } catch {
      case e:Throwable => {
        new Response("Sorry I could not understand what you said.")
      }
    }
  }
}
