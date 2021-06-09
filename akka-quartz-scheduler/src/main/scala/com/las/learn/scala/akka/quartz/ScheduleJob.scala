package com.las.learn.scala.akka.quartz

import java.util.Calendar

import akka.actor.typed.Behavior
import akka.actor.typed.scaladsl.{AbstractBehavior, ActorContext, Behaviors}
import com.las.learn.scala.akka.quartz.ScheduleJob.ExecuteTask

object ScheduleJob {
  case class ExecuteTask(second: Int)

  def apply(): Behavior[ExecuteTask] =
    Behaviors.setup(context => new ScheduleJob(context))
}

class ScheduleJob(context: ActorContext[ExecuteTask]) extends AbstractBehavior[ExecuteTask](context) {

  override def onMessage(msg: ExecuteTask): Behavior[ExecuteTask] = {
    msg match {
      case _ => println(s"${Calendar.getInstance().getTime}, i am going to execute ${msg.second}")
//      case _           => println("invalid message")
    }
    this
  }
}
