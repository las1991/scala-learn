package com.las.learn.scala.akka

import java.util.Calendar

import akka.actor.typed.ActorSystem
import akka.actor.typed.javadsl.Behaviors
import akka.http.scaladsl.Http
import akka.http.scaladsl.model.{ContentTypes, HttpEntity}
import akka.http.scaladsl.server.Directives._

import scala.io.StdIn

/**
 * @auther: liansheng
 * @Date: 2021/4/6 14:00
 * @Description:
 */
object HttpServerRoutingMinimal {

  def main(args: Array[String]): Unit = {
    implicit val system = ActorSystem(Behaviors.empty, "my-system")
    implicit val executionContext = system.executionContext

    val route = path("hello") {
      get {
        complete(HttpEntity(ContentTypes.`application/json`, "hello word" + Calendar.getInstance().getTime()))
      }
    }

    val bindingFuture = Http().newServerAt("localhost", 8080).bind(concat(route))

    println("Server online at http://localhost:8080/\nPress RETURN to stop...")
    StdIn.readLine()

    bindingFuture
      .flatMap(_.unbind())
      .onComplete(_ => system.terminate())

  }

}
