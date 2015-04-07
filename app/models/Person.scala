package models

import reactivemongo.bson.{BSONDocument, BSONDocumentReader, BSONDocumentWriter, BSONObjectID}

case class Person(_id: BSONObjectID, firstName: String, lastName: String, team: String, position: Option[String])

object Person {
  implicit object PersonReader extends BSONDocumentReader[Person] {
    def read(doc: BSONDocument): Person = {
      val id = doc.getAs[BSONObjectID]("_id").get
      val firstName = doc.getAs[String]("firstName").get
      val lastName = doc.getAs[String]("lastName").get
      val team = doc.getAs[String]("team").get
      val position = doc.getAs[String]("position")

      Person(id, firstName, lastName, team, position)
    }
  }

  implicit object PersonWriter extends BSONDocumentWriter[Person] {
    def write(t: Person): BSONDocument = BSONDocument(
      "_id" -> BSONObjectID.generate,
      "firstName" -> t.firstName,
      "lastName" -> t.lastName,
      "team" -> t.team,
      "position" -> t.position
    )
  }

  def all() = {
    Repository.persons
  }
}