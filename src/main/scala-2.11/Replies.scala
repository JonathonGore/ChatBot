/**
  * Created by jack on 2017-02-26.
  */
object Replies {
  val r = scala.util.Random

  def getRandomFeeling(): String = {
    val index = r.nextInt(2)
    val replies = Array(
      "Quite well thank you",
      "I have been better",
      "Pretty good",
      "I am just fine"
    )
    replies(index)
  }

  def unsureResponse(): String = {
    // This function has a floor of 0
    val index = r.nextInt(2)
    val replies = Array(
      "Hmm I have never thought about that",
      "I am not to sure about that",
      "I'll have to think about that"
    )
    replies(index)
  }

  def inquireInfo(): String = {
    val index = r.nextInt(3)
    val replies = Array(
      "Why don't you tell me yours",
      "What is yours",
      "How about you tell me yours",
      "What about you"
    )
    replies(index)
  }

}
