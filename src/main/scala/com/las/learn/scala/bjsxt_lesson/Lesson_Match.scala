package com.las.learn.scala.bjsxt_lesson

/**
 * @auther: liansheng
 * @Date: 2021/3/31 10:34
 * @Description:
 */
object Lesson_Match {

  def main(args: Array[String]): Unit = {
    val tp = (1, "2", 3.0, 'c', 5)
    val iterator = tp.productIterator
    iterator.foreach(MatchTesh(_))
  }

  def MatchTesh(o: Any): Unit = {
    o match {
      case i: Int => println("type is int, val: " + i)
      case s: String => println("type is string, val: " + s)
      case d: Double => println("type is double, val: " + d)
      case 'c' => println("c")
      case _ => println("no match")
    }
  }

}
