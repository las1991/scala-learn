package com.las.learn.scala.akka.quartz

import akka.actor.typed.{ActorRef, ActorSystem}
import akka.actor.typed.scaladsl.Behaviors
import com.las.learn.scala.akka.quartz.ScheduleJob.ExecuteTask
import com.typesafe.akka.`extension`.quartz.QuartzSchedulerTypedExtension

object Driver extends App {

  val system                                = ActorSystem(Behaviors.empty, "SchedulerSystem")
  val schedulerActor: ActorRef[ExecuteTask] = system.systemActorOf(ScheduleJob(), "ScheduleJob")

  // Akka Typed Actors sample.
  val schedulerTyped = QuartzSchedulerTypedExtension(system)
  schedulerTyped.scheduleTyped("every1seconds", schedulerActor, ExecuteTask(1))
  schedulerTyped.scheduleTyped("every5seconds", schedulerActor, ExecuteTask(5))

}
