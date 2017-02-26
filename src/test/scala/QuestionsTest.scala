/**
  * Created by jack on 2017-02-26.
  */
import org.scalatest.FunSuite

class QuestionsTest extends FunSuite {
  test("can split a question asked by the user") {
    val r1 = Questions.splitUserResponse("I like cheese what is your favourite colour?".split(" "))
    assert((r1.response.get :Seq[String]) == ("I like cheese".split(" ") :Seq[String]))
    assert((r1.question.get :Seq[String]) == ("what is your favourite colour?".split(" ") :Seq[String]))

    val r2 = Questions.splitUserResponse("what is your favourite colour?".split(" "))
    assert(r2.response.isEmpty)
    assert((r2.question.get :Seq[String]) == ("what is your favourite colour?".split(" ") :Seq[String]))

    val r3 = Questions.splitUserResponse("I like cheese".split(" "))
    assert((r3.response.get :Seq[String]) == ("I like cheese".split(" ") :Seq[String]))
    assert(r3.question.isEmpty)
  }
}
