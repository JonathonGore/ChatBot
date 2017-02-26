/**
  * Created by jack on 2017-02-22.
  */
import org.scalatest.FunSuite

class StringUtilTest extends FunSuite{
  test("punctuation is filtered") {
    assert(StringUtil.filterPunctuation("!@#$%^&*()-+\\[]?") == "")
    assert(StringUtil.filterPunctuation("what's your name?") == "whats your name")
    assert(StringUtil.filterPunctuation("what'''''s your name?") == "whats your name")
    assert(StringUtil.filterPunctuation("") == "")
  }
  test("contains substrings") {
    val words = Array("jackjack", "", "1311332")
    val noWords = Array.empty[String]
    assert(StringUtil.containsSubstring(words, "1"))
    assert(!StringUtil.containsSubstring(words, "12"))
    assert(StringUtil.containsSubstring(words, "jack"))
    assert(StringUtil.containsSubstring(words, "JaCK"))
    assert(!StringUtil.containsSubstring(noWords, "jack"))
  }
  test("words contain these words") {
    val question = Array("how", "are", "you")
    val words = List("you", "are", "how")
    val wordExtra = List("you", "are", "hows")
    val partialWords = List("you", "how")
    val doubleWords = List("you", "how", "you")
    val questionsTest = List("your", "fav")
    assert(StringUtil.checkIfContained(question, words))
    assert(StringUtil.checkIfContained(question, partialWords))
    assert(!StringUtil.checkIfContained(question, wordExtra))
    assert(!StringUtil.checkIfContained(question, doubleWords))
    assert(StringUtil.checkIfContained("what is your fav colour".split(" "), questionsTest))
  }
}
