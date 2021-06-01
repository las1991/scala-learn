package io.asdx.nacos4s.client.util

import java.util.Properties

import com.alibaba.nacos.api.PropertyKeyConst
import com.alibaba.nacos.client.config.NacosConfigService
import com.alibaba.nacos.client.naming.NacosNamingService
import com.typesafe.config.Config
import io.asdx.nacos4s.client.config.Nacos4sConfigService
import io.asdx.nacos4s.client.naming.Nacos4sNamingService

/**
 * @auther: liansheng
 * @Date: 2021/5/19 10:15
 * @Description:
 */
object Nacos4s {
  def configService(servAddrList: String, namespace: String): Nacos4sConfigService = {
    val props = new Properties()
    props.setProperty(PropertyKeyConst.SERVER_ADDR, servAddrList)
    props.setProperty(PropertyKeyConst.NAMESPACE, namespace)
    configService(props)
  }

  def configService(config: Config): Nacos4sConfigService = configService(ConfigUtils.toProperties(config))

  def configService(props: Properties): Nacos4sConfigService = new Nacos4sConfigService(new NacosConfigService(props))

  def namingService(servAddrList: String, namespace: String): Nacos4sNamingService = {
    val props = new Properties()
    props.setProperty(PropertyKeyConst.SERVER_ADDR, servAddrList)
    props.setProperty(PropertyKeyConst.NAMESPACE, namespace)
    namingService(props)
  }

  def namingService(config: Config): Nacos4sNamingService =
    namingService(ConfigUtils.toProperties(config))

  def namingService(props: Properties): Nacos4sNamingService =
    new Nacos4sNamingService(new NacosNamingService(props), props)

  def namingService(serverList: String): Nacos4sNamingService =
    new Nacos4sNamingService(new NacosNamingService(serverList), new Properties())
}

