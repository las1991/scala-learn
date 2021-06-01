package io.asdx.cache.redis

import scalacache._
import scalacache.memoization.{MemoizationConfig}
import scalacache.modes.sync._
import scalacache.redis._
import scalacache.serialization.binary._

/**
 * @auther: liansheng
 * @Date: 2021/5/15 02:06
 * @Description:
 */
object RedisCacheDemo {

  val KEY_USER = "user"

  def apply(host: String, port: Int): RedisCacheDemo = new RedisCacheDemo(host, port)

}

class RedisCacheDemo(val host: String, val port: Int) {
  implicit val cacheConfig = CacheConfig(cacheKeyBuilder = DefaultCacheKeyBuilder.apply(), memoization = MemoizationConfig())
  implicit val redisCache: Cache[String] = RedisCache(host, port)

  val db = scala.collection.mutable.Map(1 -> "A", 2 -> "B", 3 -> "C")

  private def queryDb(id: Int): String = {
    println(s"Getting id $id from db")
    db(id)
  }

  private def updateDb(id: Int, v: String): Unit = {
    db.update(id, v)
  }

  def getUser(id: Int) = redisCache.caching(RedisCacheDemo.KEY_USER, id)(None) {
    queryDb(id)
  }

  /**
   *
   * https://blog.cdemi.io/design-patterns-cache-aside-pattern/
   *
   * 先更新数据库，再删缓存
   *
   * 1.缓存刚好失效
   * 2.请求A查询数据库，得一个旧值
   * 3.请求B将新值写入数据库
   * 4.请求B删除缓存
   * 5.请求A将查到的旧值写入缓存
   *
   * @param id
   */
  def updateUser(id: Int, v: String) = {
    updateDb(id, v)
    remove(RedisCacheDemo.KEY_USER, id)
  }
}
