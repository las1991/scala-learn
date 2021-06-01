package io.asdx.nacos4s.client.util


import java.util.Properties
import java.util.concurrent.TimeUnit

import com.typesafe.config.{ Config, ConfigObject, ConfigValueType }

import scala.util.Try
/**
 * @auther: liansheng
 * @Date: 2021/5/19 10:16
 * @Description:
 */
object ConfigUtils {
  implicit class RichProperties(props: Properties) {
    def getInt(key: String): Try[Int] =
      Try(props.get(key) match {
        case n: java.lang.Number => n.intValue()
        case s: String           => s.toInt
      })

    def getLong(key: String): Try[Long] =
      Try(props.get(key) match {
        case n: java.lang.Number => n.longValue()
        case s: String           => s.toInt
      })

    def getBoolean(key: String): Try[Boolean] =
      Try(props.get(key) match {
        case b: java.lang.Boolean => b
        case s: String =>
          if (s == null) false
          else {
            s.toLowerCase match {
              case "on"  => true
              case "off" => false
              case text  => text.toBoolean
            }
          }
      })
  }

  def discoveryConfig(config: Config): Config = {
    val method = config.getString("akka.discovery.method")
    if (config.hasPath(s"akka.discovery.$method")) config.getConfig(s"akka.discovery.$method")
    else config.getConfig(method)
  }

  def toProperties(config: Config): Properties = {
    def make(props: Properties, parentKeys: String, obj: ConfigObject): Unit =
      obj.keySet().forEach { key =>
        val configValue = obj.get(key)
        val propKey = if (StringUtils.isNoneBlank(parentKeys)) parentKeys + "." + key else key

        if (key == Constants.TIMEOUT_MS)
          props.put(propKey, Long.box(config.getDuration(propKey, TimeUnit.MILLISECONDS)))
        else
          configValue.valueType() match {
            case ConfigValueType.OBJECT                           => make(props, propKey, configValue.asInstanceOf[ConfigObject])
            case ConfigValueType.NUMBER | ConfigValueType.BOOLEAN => props.put(propKey, configValue.unwrapped())
            case _                                                => props.put(propKey, configValue.unwrapped().toString)
          }
      }

    val props = new Properties()
    make(props, "", config.root())
    props
  }
}
