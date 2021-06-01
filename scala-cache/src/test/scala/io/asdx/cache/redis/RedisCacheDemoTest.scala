package io.asdx.cache.redis

import java.util.Objects

import io.asdx.embedded.redis.EmbeddedRedis
import org.scalatest.BeforeAndAfterAll
import org.scalatest.funspec.AnyFunSpec
import redis.clients.jedis.Jedis
import redis.embedded.RedisServer

/**
 * @auther: liansheng
 * @Date: 2021/5/15 02:09
 * @Description:
 */
class RedisCacheDemoTest extends AnyFunSpec with EmbeddedRedis with BeforeAndAfterAll {

  var redis: RedisServer = null
  var redisPort: Int = 6379

  override def beforeAll() = {
    redisPort = getFreePort
    redis = startRedis(redisPort) // A random free port is chosen

  }

  override def afterAll() = {
    stopRedis(redis)
  }

  describe("") {
    it("test redis") {
      val cache = RedisCacheDemo("localhost", redisPort)
      val jedis = new Jedis("localhost", redisPort)
      val v1 = cache.getUser(1)
      val v2 = cache.getUser(1)

      assert(Objects.equals(v1, v2))

      val res = jedis.get("user:1")
      assert(Objects.equals(res, v1))
    }
    it("test update ") {
      val cache = RedisCacheDemo("localhost", redisPort)
      val v1 = cache.getUser(1)
      val v2 = cache.getUser(1)
      cache.updateUser(1, "aaa")
      val v3 = cache.getUser(1)
      assert(Objects.equals(v1, v2))
      assert(Objects.equals(v3, "aaa"))
    }
    it("test ttl") {
      val cache = RedisCacheDemo("localhost", redisPort)
      cache.getUser(1)
      Thread.sleep(100)
      cache.getUser(1)
      Thread.sleep(500)
      cache.getUser(1)
      Thread.sleep(100)
      cache.getUser(2)
      Thread.sleep(100)
      cache.getUser(2)
      Thread.sleep(500)
      cache.getUser(2)
      Thread.sleep(100)
    }
  }
}
