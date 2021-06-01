package io.asdx.cache.guava

import io.asdx.cache.guava.GuavaCacheDemo.getUser
import org.scalatest.funspec.AnyFunSpec

/**
 * @auther: liansheng
 * @Date: 2021/5/15 01:46
 * @Description:
 */
class GuavaCacheDemoTest extends AnyFunSpec {
  describe("") {
    it("") {
      getUser(1)
      Thread.sleep(100)
      getUser(1)
      Thread.sleep(200)
      getUser(1)
      Thread.sleep(100)
      getUser(2)
      Thread.sleep(100)
      getUser(2)
      Thread.sleep(200)
      getUser(2)
      Thread.sleep(100)
    }
  }
}
