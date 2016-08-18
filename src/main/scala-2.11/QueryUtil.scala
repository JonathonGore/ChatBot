/**
  * Created by jack on 8/1/16.
  */
object QueryUtil {
  def prepareForQuery(s: String): String = {
    StringUtil.filterPunctuation(s)
      .toLowerCase()
  }
}
