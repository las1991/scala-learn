package com.las.learn.scala.bjsxt_lesson

/**
 * @auther: liansheng
 * @Date: 2021/3/31 10:29
 * @Description:
 */

trait IsEqual {
  def isEqual(o: Any): Boolean

  def isNotEqual(o: Any): Boolean
}

class Point(xx: Int, yy: Int) extends IsEqual {
  val x = xx
  val y = yy

  override def isEqual(o: Any): Boolean = {
    o.isInstanceOf[Point] && o.asInstanceOf[Point].x == this.x

  }

  override def isNotEqual(o: Any): Boolean = {
    !isEqual(o)
  }
}

object Lesson_Trait2 {
  def main(args: Array[String]): Unit = {
    val point = new Point(1, 2)
    val point1 = new Point(1, 2)
  }
}
