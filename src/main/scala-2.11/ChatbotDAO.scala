/**
  * Created by jack on 7/31/16.
  */

import java.sql.{Connection, DriverManager, ResultSet}

object ChatbotDAO {
  // connect to the database named "mysql" on the localhost
  val database = Environment.DBName
  val driver = "com.mysql.jdbc.Driver"
  val url = s"jdbc:mysql://localhost/$database"
  val username = Environment.DBUser
  val password = Environment.DBPassword

  var connection:Connection = null

  /* createConnection() Attempts to establish a connection
   * the database, will want to fail gracefully eventually..
   */
  def createConnection(): Unit = {
    try {
      Class.forName(driver)
      connection = DriverManager.getConnection(url, username, password)
      createSchema()
    } catch {
      case e: Throwable => {
        println("Unable to connect to database " + database + ".")
        e.printStackTrace()
      }
    }
  }

  /* createSchema() creates the proper database tables if they
   * do not exist.
   */
  def createSchema(): Unit = {
    println("Creating schema...")
    insert(Queries.createPeopleTable)
    insert(Queries.createMyselfTable)
    insert(Queries.createConversationsTable)
  }

  /* insert(String q) attempts to execute statement q against the database
   * (i.e, upsert or creation of table etc.)
   */
  def insert(q: String): Unit = {
    try {
      val statement = connection.createStatement()
      statement.execute(q)
    } catch {
      case e:Throwable => {
        //println(s"Unable to complete query: $q.")
        //e.printStackTrace
      }
    }
  }

  /* query(String q) attempts to execute query q against the database
   * returns a result set
   */
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

  // Do we want to be closing the connection?
  // connection.close()

}
