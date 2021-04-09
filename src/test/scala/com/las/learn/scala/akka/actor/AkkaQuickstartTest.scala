package com.las.learn.scala.akka.actor

import akka.actor.testkit.typed.scaladsl.ScalaTestWithActorTestKit
import com.las.learn.scala.akka.actor.Greeter.{Greet, Greeted}
import org.scalatest.funspec.{AnyFunSpec, AnyFunSpecLike}

/**
 * @auther: liansheng
 * @Date: 2021/4/9 11:59
 * @Description:
 */
class AkkaQuickstartTest extends ScalaTestWithActorTestKit with AnyFunSpecLike {

  describe("A Greeter") {
    it("reply to greeted") {
      val replyProbe = createTestProbe[Greeted]()
      val underTest = spawn(Greeter())
      underTest ! Greet("Santa", replyProbe.ref)
      replyProbe.expectMessage(Greeted("Santa", underTest.ref))
    }
  }

}
