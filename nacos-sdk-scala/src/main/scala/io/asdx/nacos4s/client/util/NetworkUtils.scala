package io.asdx.nacos4s.client.util

/**
 * @auther: liansheng
 * @Date: 2021/5/19 10:14
 * @Description:
 */
import java.net._

import scala.jdk.CollectionConverters._
import scala.util.Try

object NetworkUtils {
  private val validNetworkNamePrefixes = List("eth", "enp", "wlp")
  def validNetworkName(name: String): Boolean = validNetworkNamePrefixes.exists(prefix => name.startsWith(prefix))

  def interfaces(): Vector[NetworkInterface] = NetworkInterface.getNetworkInterfaces.asScala.toVector

  def onlineNetworkInterfaces(): Vector[NetworkInterface] = {
    interfaces().filterNot(ni =>
      ni.isLoopback || !ni.isUp || ni.isVirtual || ni.isPointToPoint || !validNetworkName(ni.getName))
  }

  def onlineInterfaceAddress(): Vector[InterfaceAddress] = {
    onlineNetworkInterfaces().flatMap(i => i.getInterfaceAddresses.asScala)
  }

  def firstOnlineInet4Address(): Option[InetAddress] = {
    onlineInterfaceAddress().view.filter(ia => ia.getAddress.isInstanceOf[Inet4Address]).map(_.getAddress).headOption
  }

  def toInetSocketAddress(address: String, defaultPort: Int): InetSocketAddress =
    address.split(':') match {
      case Array(host, AsInt(port)) => InetSocketAddress.createUnresolved(host, port)
      case Array(host)              => InetSocketAddress.createUnresolved(host, defaultPort)
      case _                        => throw new ExceptionInInitializerError(s"无效的通信地址：$address")
    }
}

object AsInt {
  def unapply(s: String): Option[Int] = Try(s.toInt).toOption
}
