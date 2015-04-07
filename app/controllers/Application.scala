package controllers

import models.{Repository, Person}
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

  def teams = Action.async { implicit request =>
    Repository.teams.map { teams =>
      Ok(views.html.newPerson(personForm, teams))
    }
  }

  def persons = Action.async { implicit request =>
    Repository.persons.map { persons =>
      Ok(views.html.personList(persons))
    }
  }

  def newPerson = Action.async { implicit request =>
    Repository.teams.map { teams =>
      Ok(views.html.newPerson(personForm, teams))
    }
  }

  def addPerson() = Action { implicit request =>
    val person = personForm.bindFromRequest.get
    Repository.insertPerson(person)
    Redirect(routes.Application.persons())
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