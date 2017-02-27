import org.scalatest.FunSuite

/**
  * Created by jack on 2017-02-26.
  */
class BrainTest extends FunSuite {
  test("Can get nouns") {
    val b = new Brain()
    assert(b.getNoun("my favourite car is the jaguar f type".split(" ")) == Some("car"))
    assert(b.getNoun("my fav car is the jaguar f type".split(" ")) == Some("car"))
    assert(b.getNoun("my favurite car is the jaguar f type".split(" ")) == Some("car"))
    assert(b.getNoun("the best car is the jaguar f type".split(" ")) == Some("car"))
  }
}
