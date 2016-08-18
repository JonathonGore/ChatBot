/**
  * Created by jack on 7/31/16.
  */

import scala.util.Try

object DatabaseRPC {

  private val db = ChatbotDAO

  def hasMetByName(name: String): Boolean = {
    val r = db.query(s"SELECT * FROM people WHERE name='$name' LIMIT 1;")
    r.next()
  }

  def myselfQuery(column: String): Try[String] = {
    val r = db.query("SELECT * FROM myself WHERE id=1;")
    r.next()
    Try(r.getString(column))
  }

}
