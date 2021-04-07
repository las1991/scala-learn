package com.las.learn.scala

import scala.actors.Actor

/**
 * @auther: liansheng
 * @Date: 2021/3/31 15:03
 * @Description:
 */
class MyActor1(actor: Actor) extends Actor {
  actor ! Msg(this, "hello")

  override def act(): Unit = {
    while (true) {
      receive {
        case s: String => {
          if ("hi".equals(s)) {
            println(s)
            actor ! Msg(this, "Could we hava a date")
          }
        }
        case _ => println("no match")
      }
    }
  }
}

object Lesson_Actor2 {

  def main(args: Array[String]): Unit = {
    val actor = new MyActor
    val actor1 = new MyActor1(actor)
    actor.start()
    actor1.start()
  }

}
