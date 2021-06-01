package com.las.learn.scala.akka.http.json

import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport
import spray.json.DefaultJsonProtocol

/** @auther: liansheng
  * @Date: 2021/4/6 14:14
  * @Description:
  */
final case class Item(name: String, id: Long)

final case class Order(items: List[Item])

trait JsonSupport extends SprayJsonSupport with DefaultJsonProtocol {

  implicit val itemFormat  = jsonFormat2(Item)
  implicit val orderFormat = jsonFormat1(Order) //contains List[Item]

}
