

/**
  * Created by jack on 7/17/16.
  */

class Brain {

  val db = DatabaseRPC

  /**
    * Creates response from given message and answer.
    * @param message Message to the users inputted text.
    * @param answer Answer to a new question from user.
    * @return
    */
  def buildResponse(message: String, answer: String): Response = {
    val msg = message + " " + answer
    new Response(msg)
  }

  def buildAnswer(words: Array[String], chatType: Int): Response = {
    Questions.getQuestionAnswer(words) match {
      case Some(rsp) => {
        val r = new Response(rsp.response)
        r.nextChatType = rsp.nextChatType
        r
      }
      case None => {
        val r =new Response("I am unable to understand your question.")
        r.nextChatType = ChatTypes.GENERIC
        r
      }
    }
  }


  def buildMessage(words: Array[String], chatType: Int): String = {
    chatType match {
      case ChatTypes.GET_NAME => {
        val name = Nouns.getPersonName(words); var aan = "a"
        if(WordUtil.beginsWithVowel(name)) aan = "an"
        if(db.hasMetByName(name)) return s"I have met $aan $name before."
        return s"I haven't met $aan $name before."
      }
    }
  }

  /**
    * Creates a new response to the user from the given message and chat type.
    * @param message -> Message from the user to respond to.
    * @param chatType -> The type of chat we are preparing.
    * @return A new type of Response.
    */
  def createResponse(message: String, chatType: Int): Response = {

    // Prepare raw string to be analyzed
    val filteredMsg = StringUtil.filterPunctuation(message)
    val tokens = TokenMachine.tokenize(filteredMsg)

    Questions.splitUserResponse(tokens) match {
      // User response contains an answer and a new question.
      case Questions.UserResponse(Some(msg), Some(ans)) => {
        val message = buildMessage(msg, chatType)
        val ansRsp = buildAnswer(ans, chatType)
        val rsp = buildResponse(message, ansRsp.getMessage)
        rsp.nextChatType = ansRsp.nextChatType
        rsp
      }
      // User response contains just an answer.
      case Questions.UserResponse(Some(msg), None) => {
        val message = buildMessage(msg, chatType)
        buildResponse(message, "")
      }
      // If for some reason we get a faulty response.
      case _ => {
        throw new InternalError("User response does not contain a message or an answer.")
      }
    }
  }
}
