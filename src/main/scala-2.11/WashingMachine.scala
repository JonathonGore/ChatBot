/**
  * Created by jack on 2017-02-26.
  */
object WashingMachine {


  // TODO: Fix this
  def fixWords(badWords: Array[String]): Array[String] = {
    val goodWords = Map("ur" -> "your", "u" -> "you", "wut" -> "what",
                        "wat" -> "what")

    var newWords: List[String] = List()

    for(w <- badWords) {
      newWords = (goodWords.get(w) match {
        case Some(x) => x
        case _ => w
      }) :: newWords
    }

    newWords.reverse.toArray
  }

  def Wash(dirtyText: String): Array[String] = {
    val f =StringUtil.filterPunctuation(dirtyText)
    val tokens = TokenMachine.tokenize(f)
    val properWords = fixWords(tokens)
    properWords
    //tokens
  }
}
