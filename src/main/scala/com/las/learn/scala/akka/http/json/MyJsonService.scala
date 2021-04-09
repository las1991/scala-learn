package com.las.learn.scala.akka.http.json

import akka.http.scaladsl.server.Directives

/**
 * @auther: liansheng
 * @Date: 2021/4/6 14:17
 * @Description:
 */
class MyJsonService extends Directives with JsonSupport {

  val route =
    concat(get {
      pathSingleSlash {
        complete(Item("thing", 42)) //will render as json
      }
    }, post {
      entity(as[Order]) {
        order =>
          val itemsCount = order.items.size
          val itemNames = order.items.map(_.name).mkString(",")
          complete(s"Ordered $itemsCount items: $itemNames")
      }
    })

}
