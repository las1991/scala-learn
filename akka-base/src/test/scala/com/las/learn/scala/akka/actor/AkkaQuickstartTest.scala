package com.las.learn.scala.akka.actor

import akka.actor.testkit.typed.scaladsl.ScalaTestWithActorTestKit
import com.las.learn.scala.akka.actor.Greeter.{Greet, Greeted}
import org.scalatest.funspec.AnyFunSpecLike

/** @auther: liansheng
  * @Description:
  */
class AkkaQuickstartTest extends ScalaTestWithActorTestKit with AnyFunSpecLike {

  describe("A Greeter") {
    it("reply to greeted") {
      val replyProbe = createTestProbe[Greeted]()
      val underTest  = spawn(Greeter())
      underTest ! Greet("Santa", replyProbe.ref)
      replyProbe.expectMessage(Greeted("Santa", underTest.ref))
    }
  }

}
