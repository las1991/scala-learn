package io.asdx.nacos4s.client.config

import java.time.OffsetDateTime
import java.util.concurrent.TimeUnit

import com.alibaba.nacos.api.config.listener.AbstractListener
import com.typesafe.config.ConfigFactory
import io.asdx.nacos4s.FusionFunSpecLike
import io.asdx.nacos4s.client.util.Nacos4s

/**
 * @auther: liansheng
 * @Date: 2021/5/19 10:50
 * @Description:
 */
class Nacos4sConfigServiceTest extends FusionFunSpecLike {

  // #ConfigService
  private val configService = Nacos4s.configService(ConfigFactory.parseString(
    """{
      |  serverAddr = "127.0.0.1:8848"
      |  namespace = ""
      |}""".stripMargin))
  private val dataId = "io.asdx.nacos4s"
  private val group = "DEFAULT_GROUP"
  private val timeoutMs = 30000

  describe("ConfigService") {
    val listener = new AbstractListener {
      override def receiveConfigInfo(configInfo: String): Unit = {
        println(s"[${OffsetDateTime.now()}] Received new config is:\n$configInfo")
      }
    }
    it("getServerStatus") {
      configService.getServerStatus shouldBe "UP"
    }

    it("addListener") {
      configService.addListener(dataId, group, listener)
    }

    it("publishConfig") {
      val content =
        """nacos4s.client {
          |  serverAddr = "127.0.0.1:8848"
          |  namespace = ""
          |  serviceName = "io.asdx.nacos4s"
          |}""".stripMargin
      configService.publishConfig(dataId, group, content) shouldBe true
      TimeUnit.SECONDS.sleep(1)
    }

    it("getConfig") {
      val content = configService.getConfig(dataId, group, timeoutMs)
      content should include("""serverAddr = "127.0.0.1:8848"""")
    }

    it("removeConfig") {
      TimeUnit.SECONDS.sleep(1)
      configService.removeConfig(dataId, group) shouldBe true
    }

    it("removeListener") {
      TimeUnit.SECONDS.sleep(1)
      configService.removeListener(dataId, group, listener)
    }
  }

}
