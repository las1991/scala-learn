package io.asdx.embedded.redis

import java.io.IOException
import java.net.ServerSocket
import redis.embedded.RedisServer

import scala.annotation.tailrec
import scala.concurrent.{ExecutionContext, Future}
import scala.util.{Failure, Success, Try}

/**
 * @auther: liansheng
 * @Date: 2021/4/7 11:00
 * @Description:
 */


trait EmbeddedRedis {
  @tailrec
  final def getFreePort: Int = {
    Try(new ServerSocket(0)) match {
      case Success(socket) =>
        val port = socket.getLocalPort
        socket.close()
        port
      case Failure(_: IOException) => getFreePort
      case Failure(e) => throw e
    }
  }

  type Port = Int

  def withRedis[T](port: Int = getFreePort)(f: Port => T): T = {
    val redisServer = new RedisServer(port)
    redisServer.start()
    val result = f(port)
    redisServer.stop()
    result
  }

  def withRedisAsync[T](port: Int = getFreePort)(f: Port => Future[T])(
    implicit ec: ExecutionContext): Future[T] = {

    val redisServer = new RedisServer(port)
    redisServer.start()
    f(port).map { result =>
      redisServer.stop()
      result
    }
  }

  def startRedis(port: Int = getFreePort): RedisServer = {
    val redisServer = new RedisServer(port)
    redisServer.start()
    redisServer
  }

  def stopRedis(redisServer: RedisServer): Unit = redisServer.stop()
}
