package models

import reactivemongo.bson.{BSONDocumentWriter, BSONDocument, BSONDocumentReader, BSONObjectID}

case class Team(_id: BSONObjectID, name: String)

object Team {

  implicit object TeamReader extends BSONDocumentReader[Team] {
    def read(doc: BSONDocument): Team = {
      val id = doc.getAs[BSONObjectID]("_id").get
      val name = doc.getAs[String]("name").get

      Team(id, name)
    }
  }

  implicit object TeamWriter extends BSONDocumentWriter[Team] {
    def write(t: Team): BSONDocument = BSONDocument(
      "_id" -> BSONObjectID.generate,
      "name" -> t.name
    )
  }

  def all() = Repository.teams
}