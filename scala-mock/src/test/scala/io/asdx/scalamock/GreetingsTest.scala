package io.asdx.scalamock

import io.asdx.scalamock.Greetings.Formatter
import org.scalamock.scalatest.MockFactory
import org.scalatest.funspec.AnyFunSpec

/**
 * @auther: liansheng
 * @Date: 2021/5/15 01:39
 * @Description:
 */
class GreetingsTest extends AnyFunSpec with MockFactory {

  describe("GreetingsTest") {
    it("should sayHello") {
      val mockFormatter = mock[Formatter]
      (mockFormatter.format _)
        .expects("Mr Bond")
        .returning("Ah, Mr Bond. I've been expecting you").atLeastTwice()

      (mockFormatter.format1 _)
        .expects("Mr Bond")
        .returning("Ah, Mr Bond. I've been expecting you 11") once()

      Greetings.sayHello("Mr Bond", mockFormatter)
      Greetings.sayHello1("Mr Bond", mockFormatter)
    }

    it("throwing an exception in a mock") {
      val brokenFormatter = mock[Formatter]
      (brokenFormatter.format _).expects(*).throwing(new NullPointerException).anyNumberOfTimes()
      assertThrows[NullPointerException] {
        Greetings.sayHello("Erza", brokenFormatter)
      }
    }

    it("dynamically responding to a parameter with") {
      val australianFormat = mock[Formatter]
      (australianFormat.format _).expects(*).onCall { s: String => s"G'day $s" }.twice()

      Greetings.sayHello("Wendy", australianFormat)
      Greetings.sayHello("Gray", australianFormat)
    }

    it("verifying parameters dynamically (two flavours)") {
      val teamNatsu = Set("Natsu", "Lucy", "Happy", "Erza", "Gray", "Wendy", "Carla")
      val formatter = mock[Formatter]

      def assertTeamNatsu(s: String): Unit = {
        assert(teamNatsu.contains(s))
      }

      // argAssert fails early
      (formatter.format _).expects(argAssert(assertTeamNatsu _)).onCall { s: String => s"Yo1 $s" }.once()

      // 'where' verifies at the end of the test
      (formatter.format _).expects(where { s: String => teamNatsu contains (s) }).onCall { s: String => s"Yo $s" }.twice()

      Greetings.sayHello("Carla", formatter)
      Greetings.sayHello("Happy", formatter)
      Greetings.sayHello("Lucy", formatter)
    }

    it("call ordering") {
      val mockFormatter = mock[Formatter]

      inAnyOrder {
        (mockFormatter.format _).expects("Mr Bond").returns("Ah, Mr Bond. I've been expecting you")
        (mockFormatter.format _).expects("Natsu").returns("Not now Natsu!").atLeastTwice()
      }

      Greetings.sayHello("Natsu", mockFormatter)
      Greetings.sayHello("Natsu", mockFormatter)
      Greetings.sayHello("Mr Bond", mockFormatter)
      Greetings.sayHello("Natsu", mockFormatter)
    }
  }
}