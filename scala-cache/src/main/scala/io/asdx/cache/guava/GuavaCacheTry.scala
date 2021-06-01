package io.asdx.cache.guava

import com.google.common.cache.CacheBuilder
import scalacache._
import scalacache.guava.GuavaCache
import scalacache.memoization._
import scalacache.modes.try_._

import scala.concurrent.duration.DurationInt
import scala.util.Try

/**
 * @auther: liansheng
 * @Date: 2021/5/14 18:05
 * @Description:
 */
object GuavaCacheTry {

  val underlyingGuavaCache = CacheBuilder.newBuilder().maximumSize(10000L).build[String, Entry[String]]
  implicit val guavaCache: Cache[String] = GuavaCache(underlyingGuavaCache)

  val db = Map(1 -> "A", 2 -> "B", 3 -> "C")

  def queryDb(id: Int): String = {
    println(s"Getting id $id from db")
    db(id)
  }

  def getUser(id: Int): Try[String] = memoize[Try, String](Some(150.milliseconds)) {
    queryDb(id)
  }

}
