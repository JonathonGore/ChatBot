

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
    val r = new Response(msg)
    r.nextChatType = ChatTypes.GENERIC
    r
  }

  def buildAnswer(question: Array[String], chatType: Int): Response = {
    Questions.getQuestionAnswer(question) match {
      case Some(rsp) => {
        val r = new Response(rsp.response)
        r.nextChatType = rsp.nextChatType
        r
      }
      case None => {
        val r = new Response("I am unable to understand your question.")
        r.nextChatType = ChatTypes.GENERIC
        r
      }
    }
  }

  def getInfo(words: Array[String]): Option[String] = {
    val infoPretenses = List("is")
    Nouns.getNounsAfter(infoPretenses, words).headOption
  }

  /* TODO: Add these collected nouns to the database to be used
   *
   */
  def getNoun(words: Array[String]): Option[String] = {
    val nounPretenses = List("fav", "best", "love", "my")
    Nouns.getNounsAfter(nounPretenses, words).headOption
  }

  /* This function is meant to extract info from the user
   *
   */
  def extractInfo(words: Array[String], chatType: Int): String = {
    val noun = getNoun(words)
    val info = getInfo(words)

    (noun, info) match {
      case (Some(n), Some(i)) => {
       db.insertInfo(QueryUtil.prepareForQuery(n), QueryUtil.prepareForQuery(i))
        "That sounds pretty cool!"
      }
      case (a, b) => {
        "I'm sorry I don't understand."
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
    return chatType match {
      case ChatTypes.GET_NAME => {
        getName(words, chatType)
      }
      case ChatTypes.SET_INFO => {
        extractInfo(words, chatType)
      }
      case ChatTypes.GENERIC => {
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

    // TODO: Change this to a new singleton called Washing Machine and replace
    // TODO: words like 'ur' to 'your' and 'u' to 'you'

    // Prepare raw string to be analyzed
    val tokens = WashingMachine.Wash(message)

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
      // Contains just a new question
      case Questions.UserResponse(None, Some(ans)) => {
        val message = buildAnswer(ans, chatType)
        buildResponse(message.getMessage, "")
      }
      // If for some reason we get a faulty response.
      case _ => {
        throw new InternalError("User response does not contain a message or an answer.")
      }
    }
  }
}
