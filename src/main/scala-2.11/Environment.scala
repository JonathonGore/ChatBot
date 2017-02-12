import com.typesafe.config._

/**
  * Created by jack on 2017-02-12.
  */
object Environment {

    // Load our own config values from the default location, application.conf
    val conf = ConfigFactory.load()

    val DBName = conf.getString("db.name")
    val DBUser = conf.getString("db.user")
    val DBPassword = conf.getString("db.password")

}
