package controllers.serviceA

import play.api._
import play.api.mvc._

object Application extends Controller {
  
  def home = Action {
    Ok(views.html.index("Hello there!"))
  }

  def main = Action {
    Ok("Only serviceA will respond to this.")
  }

  def greet(name: String) = Action {
    Ok(s"Hello $name!")
  }
  
}
