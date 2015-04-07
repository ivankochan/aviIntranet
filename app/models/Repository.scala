package models

import reactivemongo.api.collections.default.BSONCollection
import reactivemongo.bson.BSONDocument
import play.api.libs.concurrent.Execution.Implicits.defaultContext

object Repository {
  def getDb(): reactivemongo.api.DefaultDB = {
    import reactivemongo.api._
    import scala.concurrent.ExecutionContext.Implicits.global

    // gets an instance of the driver
    // (creates an actor system)
    val driver = new MongoDriver
    val connection = driver.connection(List("localhost"))

    // Gets a reference to the database "plugin"
      connection("intranetDb")

  }

  def persons = {
    implicit val reader = Person.PersonReader
    val db = getDb()

    val collection: BSONCollection = db("users")

    val query = BSONDocument("$query" -> BSONDocument())
    collection.find(query).cursor[Person].collect[List]()
  }
}
