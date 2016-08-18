/**
  * Created by jack on 7/31/16.
  */
import scala.util.{Try, Success, Failure}

object Questions {

  case class chatBotResponse(response: String, nextChatType: Int)
  case class UserResponse(response: Option[Array[String]], question: Option[Array[String]])

  /*
   * List of words commonly used before questions.
   * *** DONT WORRY ABOUT 'WHAT'S', 'HOW'S', ... etc as we check these as substrings so it will be taken care of.
   * *** TODO: Move this to a database and allow it to be updated. ***
   */
  val questionPretenses = List("what", "how", "who", "when", "why", "where")
  /*
   * List of words commonly associated with 'What' questions.
   * *** TODO: Move this to a database and allow it to be updated. ***
   */
  val afterWhat = List("fav", "your")

  /**
    * Splits the user response of the question asked by chatbot into the answer of the question asked by chatbot
    * and potentially if the user asked a new question.
    * @param words The response
    * @return
    */
  def splitUserResponse(words: Array[String]): UserResponse = {
      var i = 0
      for(w <- words) {
        for(p <- questionPretenses){
          if(w.contains(p)) {
            if(i == 0) {
              // If the user only replied with an question.
              return new UserResponse(None, Some(words))
            }
            else {
              // If the user answered a question, and asked a new question.
              val s = words.splitAt(i)
              return new UserResponse(Some(s._1), Some(s._2))
            }
          }
        }
        i += 1
      }
      new UserResponse(Some(words), None)
  }

  case class myselfInfo(noun: Option[String], answer: Option[String])

  /**
    * This funcion is called when the user asks a question about the ChatBot.
    * This function will look for the first noun occurring after the word your, and then
    * queries the database looking for a column matching the extracted noun.
    * @param question -> The question that has been asked. MUST be filtered.
    * @return Returns a answer to the question.
    */
  def myselfQuestion(question: Array[String], keyword: String): myselfInfo = {

    // Gets the noun we are checking about ourselves
    val i = question.indexWhere(_.contains(keyword))

    // TODO: Turn this function into a more general getWordAt function
    // TODO: possibly call it getWordAtOption and return Some if i is in range, None if not
    val noun = Nouns.getNounAtIndex(i + 1, question)

    // Prepares the noun for querying to make it possible to match a column in th database.
    val preppedNoun = QueryUtil.prepareForQuery(noun)
    DatabaseRPC.myselfQuery(preppedNoun) match {
      case Success(ans) => return myselfInfo(Some(noun), Some(ans))
      case Failure(err) => return myselfInfo(Some(noun), None)
    }
  }

  /**
    * This function is called if the user asks a question with of the 'What' type, then pushes it to the proper function.
    * @param question -> The question user asked.
    * @return An answer to the question.
    */
  def whatQuestion(question: Array[String]): Option[chatBotResponse] = {

    // Most of this function will end up looking similar except for different text I want to use.
    // We check to see if the column we searched for was valid and if it was not we ask them basically,
    // what they asked us and then save their response to our data base (A copy cat).

      if(StringUtil.checkIfContained(question, List("your", "fav"))) {
        myselfQuestion(question, "fav") match {
          case myselfInfo(Some(noun), Some(ans)) => {
            return Some(chatBotResponse(s"My favourite ${noun} is ${ans}.", ChatTypes.GENERIC))
          }
          case myselfInfo(Some(noun), None) => {
            return Some(chatBotResponse(s"I don't have a favourite $noun yet. Why don't you tell me yours?", ChatTypes.GET_FAVOURITE))
          }
        }

      }
      else if(StringUtil.checkIfContained(question, List("your"))) {
        myselfQuestion(question, "your") match {
          case myselfInfo(Some(noun), Some(ans)) => {
            return Some(chatBotResponse(s"My ${noun} is ${ans}", ChatTypes.GENERIC))
          }
          case myselfInfo(Some(noun), None) => {
            return Some(chatBotResponse(s"I don't have a $noun yet. Perhaps you could make one up for me?", ChatTypes.SET_INFO))
          }
        }
      }
      None
  }

  // Routes the question type to the correct function.
  def route(qWord: String, question: Array[String]): Option[chatBotResponse] = {
    if (qWord.contains("what"))  return whatQuestion(question)
    None
  }

  def getQuestionAnswer(question: Array[String]): Option[chatBotResponse] = {
    val first = question(0)
    for(q <- questionPretenses){
      if(first.contains(q)) {
        return route(q, question)
      }
    }
    Some(chatBotResponse("Sorry I couldn't understand your question.", ChatTypes.GENERIC))
  }
}
