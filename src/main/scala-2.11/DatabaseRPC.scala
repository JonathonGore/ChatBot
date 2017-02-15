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

  def insertInfo(key: String, value: String): Unit = {
    try {
      db.insert(s"ALTER TABLE `chatbot`.`myself` ADD COLUMN `$key` VARCHAR(45) NULL DEFAULT '$value' AFTER `birthdate`;")
    }
  }

  def insertConversation(id: String) {
    db.insert(s"INSERT INTO conversations (uuid, type) VALUES ('$id', 0);")
  }

  def getChatTypeById(id: String): Int = {
    val r = db.query(s"SELECT * FROM conversations WHERE uuid='$id' LIMIT 1;")
    val firstChatType = 0
    if(r.next) {
      r.getInt("type")
    } else {
      insertConversation(id)
      firstChatType
    }
  }

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
