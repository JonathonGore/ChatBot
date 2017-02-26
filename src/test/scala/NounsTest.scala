/**
  * Created by jack on 2017-02-22.
  */
import org.scalatest.FunSuite

class NounsTest extends FunSuite{
  test("can get a persons name") {
    assert(Nouns.getPersonName(Array("my", "name", "is", "jack")) == "jack")
    assert(Nouns.getPersonName(Array("is", "jack")) == "jack")
    assert(Nouns.getPersonName(Array("jack")) == "jack")
    assert(Nouns.getPersonName(Array("it", "is", "jack")) == "jack")
    assert(Nouns.getPersonName(Array("i", "am", "jack")) == "jack")
    assert(Nouns.getPersonName(Array("jack")) == "jack")
  }

  test("can get nouns after") {
    val pretenses = List("is", "am", "its", "it's", "it is", "im", "i'm")
    val p2 =  List("fav", "love", "my")
    assert(Nouns.getNounsAfter(pretenses, Array("I", "AM", "jack")) == ("jack" :: Nil))
    assert(Nouns.getNounsAfter(pretenses, Array("jack")) == ("jack" :: Nil))
    assert(Nouns.getNounsAfter(pretenses, Array("my", "name", "is", "jack")) == ("jack" :: Nil))
    assert(Nouns.getNounsAfter(p2, "my favourite car is the jaguar f type".split(" ")) == ("car" :: "favourite" :: Nil))
  }

}