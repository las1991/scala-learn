package io.asdx.scalamock

import net.manub.embeddedkafka.EmbeddedKafka
import org.scalatest.funspec.AnyFunSpec

/**
 * @auther: liansheng
 * @Date: 2021/4/7 13:57
 * @Description:
 */
class MockKafkaTest extends AnyFunSpec with EmbeddedKafka {

  describe("runs with embedded kafka") {
    it("work") {
      EmbeddedKafka.start()

      publishStringMessageToKafka("topic", "message")
      val msg = consumeFirstStringMessageFrom("topic")

      assert(msg equals "message")
      EmbeddedKafka.stop()
    }
  }
}
