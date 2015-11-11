package controllers.common

import org.specs2.mutable._

import play.api.test._
import play.api.test.Helpers._
import java.io.File

/**
 * Add your spec here.
 * You can mock out a whole application including requests, plugins etc.
 * For more information, consult the wiki.
 */
class ApplicationSpec extends Specification {

  val modulePath = new File("./modules/common/")
  
  "CommonApplication" should {

    "send 404 on a bad request" in {
      running(FakeApplication(path = modulePath)) {
        route(FakeRequest(GET, "/boum")) must beNone    // tbd. does not pass after Play 2.4
      }
    }
    
    "render the status page" in {
      running(FakeApplication(path = modulePath)) {
        val home = route(FakeRequest(GET, "/status")).get
        
        status(home) must equalTo(OK)
        contentAsString(home) must contain ("Everything is great")
      }
    }
  }
}
