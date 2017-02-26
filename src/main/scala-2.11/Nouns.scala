/**
  * Created by jack on 7/31/16.
  */
object Nouns {

  // Returns the best guess of the users name from tokenized input string.
  def getPersonName(words: Array[String]): String = {
    if(words.length == 1) return words(0)
    val pretenses = List("is", "am", "its", "it's", "it is", "im", "i'm")

    val s = getNounsAfter(pretenses, words)
    s.size match {
      case _ => return s.head
    }
    ""
  }

  def getNounAtIndex(n: Int, words: Array[String]): String = {
    try {
      words(n)
    } catch { case e: ArrayIndexOutOfBoundsException => ""}
  }

  def isNounMatch(word: String, key: String): Boolean = {
    word.toLowerCase().contains(key) && (word.toLowerCase()(0) == key(0))
  }

  // Looks for nouns after each item in the given list of pretenses.
  def getNounsAfter(pretenses: List[String], words: Array[String]): List[String] = {

    var s: List[String] = Nil

    for(p <- pretenses) {
      words.count(isNounMatch(_, p)) match {
        case 0 =>
        case 1 => {
          s :::= List(getNounAtIndex(words.indexWhere(isNounMatch(_, p)) + 1, words))
        }
        case x => {
          // Gets indexes of where the word in words equal p from pretenses.
          val indexes = words.zipWithIndex.collect{ case(a,b) if isNounMatch(a, p) => b}
          for(i <- indexes){
            s :::= List(getNounAtIndex(i + 1, words))
          }
        }
      }
    }

    s.isEmpty && words.length == 1 match {
      case true => {
        List(words(0))
      }
      case _ => {
        s.filter(!_.equalsIgnoreCase("")).reverse
      }
    }
  }


}
