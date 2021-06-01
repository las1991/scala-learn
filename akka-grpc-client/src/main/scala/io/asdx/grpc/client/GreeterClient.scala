package io.asdx.grpc.client

import akka.{Done, NotUsed}
import akka.actor.typed.ActorSystem
import akka.actor.typed.scaladsl.Behaviors
import akka.grpc.GrpcClientSettings
import akka.stream.scaladsl.Source
import com.google.protobuf.any.Any
import io.asdx.grpc.stub.msg.User
import io.asdx.grpc.stub.service.{GreeterService, GreeterServiceClient, HelloRequest}

import scala.concurrent.{ExecutionContext, Future}
import scala.util.{Failure, Success}

/**
 * @auther: liansheng
 * @Date: 2021/5/19 12:57
 * @Description:
 */
object GreeterClient {

  def main(args: Array[String]): Unit = {
    implicit val sys: ActorSystem[_] = ActorSystem(Behaviors.empty, "GreeterClient")
    implicit val ec: ExecutionContext = sys.executionContext

    val settings = GrpcClientSettings.fromConfig(clientName = "helloworld.GreeterService")
    val client = GreeterServiceClient(settings)

    val names =
      if (args.isEmpty) List("Alice", "Bob")
      else args.toList

    names.foreach(singleRequestReply)

    //    if (args.nonEmpty)
    //      names.foreach(streamingBroadcast)

    def singleRequestReply(name: String): Unit = {
      println(s"Performing request: $name")
      val reply = client.sayHello(HelloRequest(1L, 2, 3, 4))
      reply.onComplete {
        case Success(msg) =>
          println(msg)
          val user = msg.data.map(_.unpack[User])
          println(user)
        case Failure(e) =>
          println(s"Error: $e")
      }
    }

    //    def streamingBroadcast(name: String): Unit = {
    //      println(s"Performing streaming requests: $name")
    //
    //      val requestStream: Source[HelloRequest, NotUsed] =
    //        Source
    //          .tick(1.second, 1.second, "tick")
    //          .zipWithIndex
    //          .map { case (_, i) => i }
    //          .map(i => HelloRequest(s"$name-$i"))
    //          .mapMaterializedValue(_ => NotUsed)
    //
    //      val responseStream: Source[HelloReply, NotUsed] = client.sayHelloToAll(requestStream)
    //      val done: Future[Done] =
    //        responseStream.runForeach(reply => println(s"$name got streaming reply: ${reply.message}"))
    //
    //      done.onComplete {
    //        case Success(_) =>
    //          println("streamingBroadcast done")
    //        case Failure(e) =>
    //          println(s"Error streamingBroadcast: $e")
    //      }
    //    }

  }

}
