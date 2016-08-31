/**
  * Created by jack on 7/31/16.
  */

import java.sql.Timestamp

import scala.util.Try

object DatabaseRPC {

  private val db = ChatbotDAO

  //private def getTimeStamp(): java.sql.Timestamp = {
   // new Timestamp()
  //}

  def hasMetByName(name: String): Boolean = {
    val r = db.query(s"SELECT * FROM people WHERE name='$name' LIMIT 1;")
    r.next()
  }

  def myselfQuery(column: String): Try[String] = {
    val r = db.query("SELECT * FROM myself WHERE id=1;")
    r.next()
    Try(r.getString(column))
  }

  def insertMyself(noun: String, info: String): Unit = {
    /** TODO ADD ROW TO DATA
      * 
      */
  }

  def insertMyselfPerson(name: String): Unit = {
    // INSERT INTO PEOPLE
    /**
      * TODO: Fix timestamp that is to be inserted.
      */
    //db.query(s"INSERT INTO people (name, firstmet) VALUES ('$name', ${getTimeStamp()});")
    db.insert(s"INSERT INTO people (name) VALUES ('$name');")
  }

}
