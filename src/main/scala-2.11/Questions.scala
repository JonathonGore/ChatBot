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
  val questionPretenses = List("what", "how", "who", "when", "why", "where", "do")
  /*
   * List of words commonly associated with 'What' questions.
   * *** TODO: Move this to a database and allow it to be updated. ***
   */
  val afterWhat = List("fav", "your")

  /**
    * TODO: Change this function to return list of questions instend of a single question and a single response
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
              return UserResponse(None, Some(words))
            }
            else {
              // If the user answered a question, and asked a new question.
              val s = words.splitAt(i)
              return UserResponse(Some(s._1), Some(s._2))
            }
          }
        }
        i += 1
      }
      UserResponse(Some(words), None)
  }

  case class myselfInfo(noun: Option[String], answer: Option[String])

  /**
    * This funcion is called when the user asks a question about the ChatBot.
    * This function will look for the first noun occurring after the keyword, and then
    * queries the database looking for a column matching the extracted noun.
    * @param question -> The question that has been asked. MUST be filtered.
    * @return Returns a answer to the question.
    */
  def myselfQuestion(question: Array[String], keyword: String): myselfInfo = {

    // Gets the noun after the keyword
    val i = question.indexWhere(_.contains(keyword))

    // TODO: Turn this function into a more general getWordAt function
    // TODO: possibly call it getWordAtOption and return Some if i is in range, None if not
    val noun = Nouns.getNounAtIndex(i + 1, question)

    // Prepares the noun for querying to make it possible to match a column in th database.
    val preppedNoun = QueryUtil.prepareForQuery(noun)
    DatabaseRPC.myselfQuery(preppedNoun) match {
      case Success(ans) => myselfInfo(Some(noun), Some(ans))
      case Failure(err) => myselfInfo(Some(noun), None)
    }
  }

  def getGenericInfo(question: Array[String], kind: String): Option[chatBotResponse] = {
    myselfQuestion(question, "your") match {
      case myselfInfo(Some(noun), Some(ans)) => {
        Some(chatBotResponse(s"My ${noun} is ${ans}, ${Replies.inquireInfo()}?", ChatTypes.GET_INFO))
      }
      case myselfInfo(Some(noun), None) => {
        Some(chatBotResponse(s"${Replies.unsureResponse()}, ${kind} is your ${noun}?", ChatTypes.GET_INFO))
      }
    }
  }

  def getGenericFeeling(): chatBotResponse = {
    chatBotResponse(Replies.getRandomFeeling + ".", ChatTypes.GENERIC)
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
            return Some(chatBotResponse(s"I don't have a favourite $noun yet. ${Replies.inquireInfo()}?", ChatTypes.GET_FAVOURITE))
          }
        }

      }
      else if(StringUtil.checkIfContained(question, List("your"))) {
        return getGenericInfo(question, "what")
      }
      None
  }

  def howAnalyzer(question: Array[String]): Option[chatBotResponse] = {
    val howPretenses = List("so", "that", "this", "such")
    Nouns.getNounsAfter(howPretenses, question).headOption match {
      case Some(x) => return getGenericInfo(question, "how")
      case None => {
        return Some(getGenericFeeling())
      }
    }
    None
  }


  def howQuestion(question: Array[String]): Option[chatBotResponse] = {
    if(StringUtil.checkIfContained(question, List("are", "you"))) {
        return howAnalyzer(question)
    }
    else if(StringUtil.checkIfContained(question, List("is", "your"))) {
        return getGenericInfo(question, "how")
    }
    else if(StringUtil.checkIfContained(question, List("are", "you"))) {
      return getGenericInfo(question, "how")
    }
    None
  }

  def whenQuestion(question: Array[String]): Option[chatBotResponse] = {
    if(StringUtil.checkIfContained(question, List("your"))) {
      return getGenericInfo(question, "when")
    }
    else if(StringUtil.checkIfContained(question, List("is")) ||
      StringUtil.checkIfContained(question, List("was"))) {
      // TODO: Add code to retrieve and store dates
      // Consider using some sort of API
    }
    None
  }

  def doQuestion(question: Array[String]): Option[chatBotResponse] = {
    if(StringUtil.checkIfContained(question, List("your"))) {
      return getGenericInfo(question, "when")
    }
    None
  }

  // Routes the question type to the correct function.
  def route(qWord: String, question: Array[String]): Option[chatBotResponse] = {
    if (qWord.contains("what"))  return whatQuestion(question)
    else if (qWord.contains("when"))  return whenQuestion(question)
    else if (qWord.contains("how"))  return howQuestion(question)
    else if(qWord.contains("do")) return doQuestion(question)
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
