package io.asdx.cache.guava

import io.asdx.cache.guava.GuavaCacheTry.getUser
import org.scalatest.funspec.AnyFunSpecLike

/**
 * @auther: liansheng
 * @Date: 2021/5/14 23:09
 * @Description:
 */
class GuavaCacheTryTest extends AnyFunSpecLike{

  describe(""){
    it(""){
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
