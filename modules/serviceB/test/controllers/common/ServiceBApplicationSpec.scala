package controllers.serviceB

import org.specs2.mutable._

import play.api.test._
import play.api.test.Helpers._

/**
 * Add your spec here.
 * You can mock out a whole application including requests, plugins etc.
 * For more information, consult the wiki.
 */
class ServiceBApplicationSpec extends Specification {
  
  "ServiceBApplication" should {
    
    "send 404 on a bad request" in {
      running(FakeApplication()) {
        route(FakeRequest(GET, "/boum")) must beNone        
      }
    }

    "respond to serviceB specific requests" in {
      running(FakeApplication()) {
        val main = route(FakeRequest(GET, "/serviceB")).get
        
        status(main) must equalTo(OK)
        contentType(main) must beSome.which(_ == "text/html")
        contentAsString(main) must contain ("This is serviceB")

        val lottery = route(FakeRequest(GET, "/serviceB/lottery")).get
        
        status(lottery) must equalTo(OK)
        contentType(lottery) must beSome.which(_ == "text/html")
        contentAsString(lottery) must contain ("Your lucky lottery numbers are")
      }
    }

  }
}
