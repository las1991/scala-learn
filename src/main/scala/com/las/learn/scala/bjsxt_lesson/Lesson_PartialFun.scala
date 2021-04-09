package com.las.learn.scala.bjsxt_lesson

/**
 * @auther: liansheng
 * @Date: 2021/3/31 10:47
 * @Description:
 */
object Lesson_PartialFun {

  def main(args: Array[String]): Unit = {
    println(myTest("a"))
  }

  def myTest: PartialFunction[String, Int] = {
    case "1" => 1
    case "a" => 'a'
    case _ => 0
  }

}
