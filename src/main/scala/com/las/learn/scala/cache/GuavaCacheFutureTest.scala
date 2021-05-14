package com.las.learn.scala.cache

import com.google.common.cache.CacheBuilder
import com.las.learn.scala.cache.GuavaCacheTryTest.getUser
import scalacache._
import scalacache.guava.GuavaCache
import scalacache.memoization._
import scalacache.modes.scalaFuture._

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future
import scala.concurrent.duration.DurationInt


/**
 * @auther: liansheng
 * @Date: 2021/5/14 18:05
 * @Description:
 */
object GuavaCacheFutureTest {

  val underlyingGuavaCache = CacheBuilder.newBuilder().maximumSize(10000L).build[String, Entry[String]]
  implicit val guavaCache: Cache[String] = GuavaCache(underlyingGuavaCache)

  val db = Map(1 -> "A", 2 -> "B", 3 -> "C")

  def queryDb(id: Int): String = {
    println(s"Getting id $id from db")
    db(id)
  }

  def getUser(id: Int): Future[String] = memoize[Future, String](Some(5.seconds)) {
    queryDb(id)
  }

  def main(args: Array[String]): Unit = {
    getUser(1)
    Thread.sleep(100)
    getUser(1)
    Thread.sleep(5000)
    getUser(1)
    Thread.sleep(100)
    getUser(2)
    Thread.sleep(100)
    getUser(2)
    Thread.sleep(5000)
    getUser(2)
    Thread.sleep(100)
  }

}
