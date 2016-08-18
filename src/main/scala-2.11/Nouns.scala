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

    return "";
  }

  def getNounAtIndex(n: Int, words: Array[String]): String = {
    try {
      words(n)
    } catch { case e: ArrayIndexOutOfBoundsException => ""}
  }

  // Looks for nouns after each item in the given list of pretenses.
  private def getNounsAfter(pretenses: List[String], words: Array[String]): List[String] = {

    var s: List[String] = Nil

    for(p <- pretenses) {
      words.count(_.equalsIgnoreCase(p)) match {
        case 0 =>
        case 1 => {
          s :::= List(getNounAtIndex(words.indexWhere(_.equalsIgnoreCase(p)) + 1, words))
        }
        case x => {
          // Gets indexes of where the word in words equal p from pretenses.
          val indexes = words.zipWithIndex.collect{ case(a,b) if a.equalsIgnoreCase(p) => b}
          for(i <- indexes){
            s :::= List(getNounAtIndex(i + 1, words))
          }
        }
      }
    }
    s.filter(!_.equalsIgnoreCase(""))
  }


}
