/**
  * Created by jack on 7/17/16.
  */

class Response(msg: String) {

  private val message: String = msg
  var nextChatType: Int = ChatTypes.GENERIC

  def getMessage: String = {
    return message
  }
}