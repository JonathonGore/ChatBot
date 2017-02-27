import org.scalatest.FunSuite

/**
  * Created by jack on 2017-02-26.
  */
class WashingMachineTest extends FunSuite{
  test("can fix words like 'ur'") {
    assert(WashingMachine.fixWords("ur stupid".split(" ")).deep == "your stupid".split(" ").deep)
  }
}
