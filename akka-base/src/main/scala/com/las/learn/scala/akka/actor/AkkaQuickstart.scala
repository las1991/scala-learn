package com.las.learn.scala.akka.actor

import akka.actor.typed.scaladsl.Behaviors
import akka.actor.typed.{ActorRef, ActorSystem, Behavior}
import com.las.learn.scala.akka.actor.GreeterMain.SayHello

/** @auther: liansheng
  * @Date: 2021/4/9 11:12
  * @Description:
  */

object Greeter {

  final case class Greet(whom: String, replyTo: ActorRef[Greeted])

  final case class Greeted(whom: String, from: ActorRef[Greet])

  def apply(): Behavior[Greet] = Behaviors.receive({ (context, message) =>
    context.log.info("Hello {{!", message.whom)
    message.replyTo ! Greeted(message.whom, context.self)
    Behaviors.same
  })

}

object GreeterBot {

  def bot(greetingCounter: Int, max: Int): Behavior[Greeter.Greeted] =
    Behaviors.receive { (context, msg) =>
      val n = greetingCounter + 1
      context.log.info("Greeting {} for {}", n, msg.whom)
      if (n == max) {
        Behaviors.stopped
      } else {
        msg.from ! Greeter.Greet(msg.whom, context.self)
        bot(n, max)
      }
    }

  def apply(max: Int): Behavior[Greeter.Greeted] =
    bot(0, max)
}

object GreeterMain {

  final case class SayHello(name: String)

  def apply(): Behavior[SayHello] =
    Behaviors.setup({ context =>
      val greeter = context.spawn(Greeter(), "greeter")
      Behaviors.receiveMessage({ msg =>
        val replyTo = context.spawn(GreeterBot(max = 3), msg.name)
        greeter ! Greeter.Greet(msg.name, replyTo)
        Behaviors.same
      })
    })

}

object AkkaQuickstart {
  val greeterMain: ActorSystem[GreeterMain.SayHello] = ActorSystem(GreeterMain(), "AkkaQuickStart")

  greeterMain ! SayHello("Charles")

}
