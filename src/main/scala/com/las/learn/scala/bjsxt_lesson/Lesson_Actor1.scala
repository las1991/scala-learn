package com.las.learn.scala.bjsxt_lesson

import akka.actor.Actor


/**
 * @auther: liansheng
 * @Date: 2021/3/31 14:56
 * @Description:
 */

case class Msg(actor: Actor, msg: String) {

}

class MyActor extends Actor {
  override def receive: Receive = {
    case msg: Msg => {
      if ("hello".equals(msg.msg)) {
        msg.actor.self ! "hi"
      } else {
        println(msg.msg)
      }
    }
    case s: String => println(s)
    case _ => println("no match")
  }

}


object Lesson_Actor1 {
  def main(args: Array[String]): Unit = {
    val actor = new MyActor

    actor.self ! "100"
    actor.self ! 1
  }
}
