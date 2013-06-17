package controllers.serviceA

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

  val modulePath = new File("./modules/serviceA/")
  
  "ServiceAApplication" should {
    
    "send 404 on a bad request" in {
      running(FakeApplication(path = modulePath)) {
        route(FakeRequest(GET, "/boum")) must beNone        
      }
    }
    
    "render the home page" in {
      running(FakeApplication(path = modulePath)) {
        val home = route(FakeRequest(GET, "/")).get
        
        status(home) must equalTo(OK)
        contentType(home) must beSome.which(_ == "text/html")
        contentAsString(home) must contain ("Hello there")
      }
    }

    "respond to serviceA specific requests" in {
      running(FakeApplication(path = modulePath)) {
        val main = route(FakeRequest(GET, "/serviceA")).get
        
        status(main) must equalTo(OK)
        contentAsString(main) must contain ("Only serviceA will respond to this")

        val helloGeorge = route(FakeRequest(GET, "/serviceA/George")).get
        
        status(helloGeorge) must equalTo(OK)
        contentAsString(helloGeorge) must contain ("Hello George")
      }
    }

  }
}