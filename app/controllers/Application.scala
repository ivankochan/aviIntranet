package controllers

import models.Person
import play.api.data.Forms._
import play.api.data.Form
import play.api.mvc._
import play.api.libs.concurrent.Execution.Implicits.defaultContext
import reactivemongo.bson.BSONObjectID

object Application extends Controller {

  def index = Action {

//    val user = Repository.getUser()

    Ok("Hello World!")
  }

  def persons = Action.async { implicit request =>
    Person.all().map { persons =>
      Ok(views.html.index(persons, personForm))
    }
  }

  val personForm: Form[Person] = Form {
    mapping(
      "firstName" -> nonEmptyText,
      "lastName" -> nonEmptyText,
      "team" -> text,
      "position" -> text
    ){(firstName, lastName, team, position) =>
      val pos: Option[String] = if(position.nonEmpty) Some(position) else None
      Person(BSONObjectID.generate, firstName, lastName, team, pos)
    } {person =>
      Some(
        person.firstName,
        person.lastName,
        person.team,
        person.position.getOrElse("abc")
      )}
  }
}