

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

  def extractInfo(words: Array[String], chatType: Int): Unit = {
    val nounPretenses: List[String] = List("fav", "love", "my")
    val noun = Nouns.getNounsAfter(nounPretenses, words).headOption

    val infoPretenses: List[String] = List("is")
    val info =  Nouns.getNounsAfter(infoPretenses, words).headOption

    (noun, info) match {
      case (Some(n), Some(i)) => {
        db.insertInfo(QueryUtil.prepareForQuery(n), QueryUtil.prepareForQuery(i))
        return "That sounds pretty cool!"
      }
      case (a, b) => {
        return "I'm sorry I don't understand."
      }
    }
  }

  def getName(words: Array[String], chatType: Int): String = {
    val name = Nouns.getPersonName(words); var aan = "a"
    if(WordUtil.beginsWithVowel(name)) aan = "an"
    if(db.hasMetByName(name)) return s"I have met $aan $name before."
    else {
      // Insert name into people.
      db.insertMyselfPerson(name)
      return s"I haven't met $aan $name before."
    }
  }

  def buildMessage(words: Array[String], chatType: Int): String = {
    chatType match {
      case ChatTypes.GET_NAME => {
        getName(words, chatType)
      }
      case ChatTypes.SET_INFO => {
        extractInfo(words, chatType)
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
