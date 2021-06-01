package io.asdx.nacos4s.client.naming

import java.util.concurrent.TimeUnit

import com.typesafe.config.ConfigFactory
import io.asdx.nacos4s.FusionFunSpecLike
import io.asdx.nacos4s.client.util.Nacos4s

/**
 * @auther: liansheng
 * @Date: 2021/5/19 10:23
 * @Description:
 */
class Nacos4sNamingServiceTest extends FusionFunSpecLike {

  private val serviceName = "io.asdx.nacos4s.service.demo"
  private val group = "DEFAULT_GROUP"
  private val ip = "127.0.0.1"
  private val port = 8888

  private val namingService = Nacos4s.namingService(ConfigFactory.parseString(
    s"""{
       |  serverAddr = "127.0.0.1:8848"
       |  namespace = ""
       |  autoRegisterInstance = true
       |  serviceName = "${serviceName}-auto-register"
       |  ip= "127.0.0.1"
       |  port = 9999
       |}""".stripMargin))

  describe("Nacos4sNamingService") {
    it("registerInstance") {
      namingService.registerInstance(serviceName, group, ip, port)
      TimeUnit.SECONDS.sleep(1)
      val inst = namingService.selectOneHealthyInstance(serviceName)
      inst should not be null
      inst.isHealthy shouldBe true
      inst.isEnabled shouldBe true
      inst.isEphemeral shouldBe true
      inst.getServiceName shouldBe s"$group@@$serviceName"
      println(inst)
    }
    it("getServerStatus") {
      namingService.getServerStatus shouldBe "UP"
      TimeUnit.SECONDS.sleep(1)
    }
    it("getAllInstances") {
      val insts = namingService.getAllInstances(s"${serviceName}-auto-register")
      insts.foreach(println)
      insts should have size 1
    }
  }
}
