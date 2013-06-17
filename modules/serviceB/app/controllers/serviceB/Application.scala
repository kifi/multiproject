package controllers.serviceB

import play.api._
import play.api.mvc._
import scala.util.Random

object Application extends Controller {
  
  def main = Action {
    Ok("This is serviceB!")
  }

  def lottery = Action {
    val lotteryNumbers = Seq.fill(5)(Random.nextInt(40)).mkString(" ")
    Ok(s"Your lucky lottery numbers are $lotteryNumbers")
  }
  
}