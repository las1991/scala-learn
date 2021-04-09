package com.las.learn.scala.bjsxt_lesson

/**
 * @auther: liansheng
 * @Date: 2021/3/31 10:11
 * @Description:
 */
object Lesson_Tuple {

  def main(args: Array[String]): Unit = {
    val tuple: Tuple1[String] = new Tuple1("hello")
    val tuple2 = new Tuple2(1, "hello")
    val tuple3: Tuple3[Int, Int, Int] = Tuple3(1, 2, 3)
    val tuple6: Tuple6[Int, Int, Int, Int, Int, Int] = (1, 2, 3, 4, 5, 6)

    val iterator = tuple3.productIterator
    iterator.foreach(println(_))
    while (iterator.hasNext) {
      println(iterator.next())
    }

    val swap = tuple2.swap
  }

}
