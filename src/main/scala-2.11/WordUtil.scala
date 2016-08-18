/**
  * Created by jack on 7/31/16.
  */
object WordUtil {

  val vowels = List("a", "e", "i", "o", "u")

  def beginsWithVowel(word: String): Boolean = {
    val c = word.toLowerCase.substring(0, 1)
    vowels.contains(c)
  }
}
