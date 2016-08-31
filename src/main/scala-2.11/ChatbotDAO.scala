/**
  * Created by jack on 7/31/16.
  */

import java.sql.{Connection, DriverManager, ResultSet}

object ChatbotDAO {
  // connect to the database named "mysql" on the localhost
  val database = "chatbot"
  val driver = "com.mysql.jdbc.Driver"
  val url = s"jdbc:mysql://localhost/$database"
  val username = "root"
  val password = "root"

  var connection:Connection = null
  createConnection()

  // Attempts to create a connection to database.
  def createConnection(): Unit = {
    try {
      Class.forName(driver)
      connection = DriverManager.getConnection(url, username, password)
    } catch {
      case e: Throwable => {
        println("Unable to connect to database " + database + ".")
        e.printStackTrace
      }
    }
  }

  def insert(q: String): Unit = {
    try {
      val statement = connection.createStatement()
      statement.execute(q)
    } catch {
      case e:Throwable => {
        println(s"Unable to complete query: $q.")
        e.printStackTrace
      }
    }
  }

  // Queries data base with given query q.
  def query(q: String): ResultSet = {
    try {
      val statement = connection.createStatement()
      statement.executeQuery(q)
    } catch {
      case e:Throwable => {
        println(s"Unable to complete query: $q.")
        e.printStackTrace
        null
      }
    }
  }

  // connection.close()

}
