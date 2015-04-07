package models

import reactivemongo.api.collections.default.BSONCollection
import reactivemongo.bson.BSONDocument
import play.api.libs.concurrent.Execution.Implicits.defaultContext

object Repository {
  private val PERSONS_COL_NAME = "persons"
  private val TEAMS_COL_NAME = "teams"

  lazy val personsCollection: BSONCollection = db(PERSONS_COL_NAME)
  lazy val teamsCollection: BSONCollection = db(TEAMS_COL_NAME)

  private def db: reactivemongo.api.DefaultDB = {
    import reactivemongo.api._

    val driver = new MongoDriver
    val connection = driver.connection(List("localhost"))
    connection("intranetDb")
  }

  def persons = {
    implicit val reader = Person.PersonReader

    val query = BSONDocument("$query" -> BSONDocument())
    personsCollection.find(query).cursor[Person].collect[List]()
  }

  def insertPerson(person: Person) = {
    personsCollection.insert(person)
  }

  def teams = {
    implicit val reader = Team.TeamReader

    val query = BSONDocument("$query" -> BSONDocument())
    teamsCollection.find(query).cursor[Team].collect[List]()
  }
}
