package com.las.learn.scala

import scala.collection.mutable.ListBuffer

/**
 * @auther: liansheng
 * @Date: 2021/3/30 15:32
 * @Description:
 */
object Lesson_List {

  def main(args: Array[String]): Unit = {
    val list = List[Int](1, 2, 3)
    list.foreach(println(_))
    //    for (ele <- list) {
    //      println(ele)
    //    }
    val list1: List[String] = List[String]("hello 1", "hello 2", "hello 3")
    val result = list1.+("aaa")

    val stringses = list1.map(ele => ele.split(" "))
    for (elem <- stringses) {
      println("新数组")
      elem.foreach(println(_))
    }

    val strings = list1.flatMap(s => s.split(" "))
    strings.foreach(println(_))

    val ints = list.filter(e => e > 1)
    ints.foreach(println(_))

    val i = list.count(e => e > 2)

    val ints1 = ListBuffer[Int](1, 2, 3, 4, 5, 6)
  }
}
