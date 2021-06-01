package io.asdx.nacos4s.client.config

/**
 * @auther: liansheng
 * @Date: 2021/5/19 10:17
 * @Description:
 */
import com.alibaba.nacos.api.config.ConfigService
import com.alibaba.nacos.api.config.listener.Listener

class Nacos4sConfigService(val underlying: ConfigService) {
  def getConfig(dataId: String, group: String, timeoutMs: Long): String = underlying.getConfig(dataId, group, timeoutMs)

  def getConfigAndSignListener(dataId: String, group: String, timeoutMs: Long, listener: Listener): String =
    underlying.getConfigAndSignListener(dataId, group, timeoutMs, listener)

  def addListener(dataId: String, group: String, listener: Listener): Unit =
    underlying.addListener(dataId, group, listener)

  def publishConfig(dataId: String, group: String, content: String): Boolean =
    underlying.publishConfig(dataId, group, content)

  def removeConfig(dataId: String, group: String): Boolean = underlying.removeConfig(dataId, group)

  def removeListener(dataId: String, group: String, listener: Listener): Unit =
    underlying.removeListener(dataId, group, listener)

  def getServerStatus: String = underlying.getServerStatus

  def shutDown(): Unit = underlying.shutDown()
}

