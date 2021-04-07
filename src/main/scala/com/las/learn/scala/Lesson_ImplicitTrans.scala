package com.las.learn.scala

/**
 * @auther: liansheng
 * @Date: 2021/3/31 14:17
 * @Description:
 */
object Lesson_ImplicitTrans {

  def sayName(implicit name: String) = {
    println(s"$name is a student ......")
  }

  def sayHello(age: Int)(implicit name: String) = {
    println(s"$name is a student ......,age = $age")
  }

  def main(args: Array[String]): Unit = {
    implicit val name: String = "zhangsan"
    sayName
    sayHello(18)
  }
}
