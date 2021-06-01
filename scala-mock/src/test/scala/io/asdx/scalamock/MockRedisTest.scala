package io.asdx.scalamock

import com.redis.RedisClient
import io.asdx.embedded.redis.EmbeddedRedis
import org.scalatest.BeforeAndAfterAll
import org.scalatest.funspec.AnyFunSpec
import redis.embedded.RedisServer

/**
 * @auther: liansheng
 * @Date: 2021/4/1 10:10
 * @Description:
 */
class MockRedisTest extends AnyFunSpec with EmbeddedRedis with BeforeAndAfterAll {

  var redis: RedisServer = null
  var redisPort: Int = 0

  override def beforeAll() = {
    redisPort = getFreePort
    redis = startRedis(redisPort) // A random free port is chosen
  }

  describe("") {
    it("something with redis") {
      // ...
    }
  }

  override def afterAll() = {
    stopRedis(redis)
  }

  describe("withRedis") {
    it("start redis on a random free port") {

      withRedis() {
        port =>
          val redis = new RedisClient("localhost", port)
          val ping = redis.ping
          assert(ping.map(_ equals "PONG").getOrElse(false))
      }
    }

  }

}
