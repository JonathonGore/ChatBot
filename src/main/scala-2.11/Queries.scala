/**
  * Created by jack on 2017-02-12.
  */
object Queries {
    val createPeopleTable = "CREATE TABLE IF NOT EXISTS `chatbot`.`people` (" +
                            "`id` INT NOT NULL AUTO_INCREMENT," +
                            "`uuid` CHAR(36) NULL," +
                            "`name` VARCHAR(45) NULL," +
                            "`lastmet` DATETIME NULL," +
                            "PRIMARY KEY (`id`)," +
                            "UNIQUE INDEX `uuid_UNIQUE` (`uuid` ASC));"

    val createMyselfTable = "CREATE TABLE IF NOT EXISTS `chatbot`.`myself` (" +
                            "`id` INT NOT NULL AUTO_INCREMENT," +
                            "`name` VARCHAR(45) NULL," +
                            "`birthdate` DATE NULL," +
                            "PRIMARY KEY (`id`));"

    val createConversationsTable =  "CREATE TABLE IF NOT EXISTS `chatbot`.`conversations` (" +
                                    "`uuid` CHAR(36) NOT NULL," +
                                    "`type` INT NULL," +
                                    "PRIMARY KEY (`uuid`)," +
                                    "UNIQUE INDEX `uuid_UNIQUE` (`uuid` ASC));"

}
