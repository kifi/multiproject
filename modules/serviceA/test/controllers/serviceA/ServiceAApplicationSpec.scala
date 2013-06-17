package controllers.serviceA

import org.specs2.mutable._

import play.api.test._
import play.api.test.Helpers._

/**
 * Add your spec here.
 * You can mock out a whole application including requests, plugins etc.
 * For more information, consult the wiki.
 */
class ServiceAApplicationSpec extends Specification {
  
  "ServiceAApplication" should {
    
    "send 404 on a bad request" in {
      running(FakeApplication()) {
        route(FakeRequest(GET, "/boum")) must beNone        
      }
    }
    
    "render the home page" in {
      running(FakeApplication()) {
        val home = route(FakeRequest(GET, "/")).get
        
        status(home) must equalTo(OK)
        contentType(home) must beSome.which(_ == "text/html")
        contentAsString(home) must contain ("Hello there")
      }
    }

    "respond to serviceA specific requests" in {
      running(FakeApplication()) {
        val main = route(FakeRequest(GET, "/serviceA")).get
        
        status(main) must equalTo(OK)
        contentType(main) must beSome.which(_ == "text/html")
        contentAsString(main) must contain ("Only serviceA will respond to this")

        val helloGeorge = route(FakeRequest(GET, "/serviceA/George")).get
        
        status(helloGeorge) must equalTo(OK)
        contentType(helloGeorge) must beSome.which(_ == "text/html")
        contentAsString(helloGeorge) must contain ("Hello George")
      }
    }

  }
}