package com.las.learn.scala.bjsxt_lesson

/**
 * @auther: liansheng
 * @Date: 2021/3/30 16:02
 * @Description:
 */
object Lesson_Map {

  def main(args: Array[String]): Unit = {
    val map = Map[Int, String](1 -> "100", 2 -> "200", 3 -> "300", 4 -> "400")
    val str = map.get(1).getOrElse("")

    map.filter(elem => {
      elem._1 > 1
    })

    val map1 = Map[Int, String]((1, "1"), (2, "2"), (3, "300"), (4, "4"))
    val intToString = map.++(map1)

    val map2 = scala.collection.mutable.Map[Int, String](1 -> "1", 2 -> "2", 3 -> "3", 4 -> "4")
    map2.put(5, "500")

  }

}
