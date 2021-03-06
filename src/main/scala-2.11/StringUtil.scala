/**
  * Created by jack on 7/31/16.
  */
object StringUtil {
  /**
    * Filters punctuation from given string s.
    * @param s -> string to be filtered.
    * @return Filtered string.
    */
  def filterPunctuation(s: String): String = {
    val punctuation = "!@#$%^&*(),.?':;\"-_=+\\]['"
    s.filter(!punctuation.contains(_))
  }
  /**
    * Returns true if any of the words contain the given substring, false otherwise.
    * @param words -> The words to be checked through.
    * @param subString -> The string to check for.
    * @return Returns true if substring is contained in one or more of the words.
    */
  def containsSubstring(words: Array[String], subString: String): Boolean = {
    for(w <- words){
      if(w.toLowerCase.contains(subString.toLowerCase)) return true
    }
    false
  }

  case class MessageParams(message: String, id: String)

  /** Splits the params at & and puts values in to the case class
    *
    * @param body params of a POST request
    * @return MessageParams case class
    */
  def parseMessageParams(body: String): MessageParams = {
    val params = body.split("&")
    MessageParams(params(0).split("=")(1), params(1).split("=")(1))
  }


  /**
    * Returns true if all strings in words are contained in the question.
    * @param question -> The question to check for strings.
    * @param words -> Strings to check for in question.
    * @return Returns true if all words are contained in question.
    */
  def checkIfContained(question: Array[String], words: List[String]): Boolean = {
    var count = 0
    for(w <- words.distinct){
      if(StringUtil.containsSubstring(question, w)) count += 1
    }
    count >= words.length
  }
}
