package com.las.learn.scala.bjsxt_lesson

import java.util.Date

/**
 * @auther: liansheng
 * @Date: 2021/3/18 17:56
 * @Description:
 */
object Lesson_function {
  def max(x: Int, y: Int): Int = if (x > y) x else y

  def max1(x: Int, y: Int) {
    if (x > y) x else y
  }

  def main(args: Array[String]): Unit = {
    println(max(1, 100))
    println(max1(1, 100))
    println(fun(5))
    println(fun3())
    println(fun3(100))
    println(fun3(b = 100))
    println(fun4())
    println(fun5(1, 2))
    fun70("a")
    fun70("b")
    println(fun80(fun8, "scala"))
    println(fun80((a: Int, b: Int) => a * b, "scala"))
    println(fun81("a")("b", "c"))
    println(fun82((a, b) => a + b)("hello", "word"))
    println(fun9(1, 2)(3, 4))
  }

  /**
   * 2.递归方法
   * 递归方法显示声明方法返回类型
   */
  def fun(num: Int): Int = {
    if (num == 1) 1 else num * fun(num - 1)
  }

  /**
   * 3.参数有默认值的方法
   */
  def fun3(a: Int = 10, b: Int = 20): Int = {
    a + b
  }

  /**
   * 4.可变长参数的方法
   */
  def fun4(s: String*) = {
    for (elem <- s) {
      println(elem)
    }
    //    s.foreach(elem => println(elem))
    s.foreach(println)
  }

  /**
   * 5.匿名函数
   */
  def fun5 = (a: Int, b: Int) => {
    a + b
  }

  /**
   * 6.嵌套方法
   */
  def fun6(num: Int) = {
    def fun(a: Int) = {
      a + 1
    }

    fun(num)
  }

  /**
   * 7.偏应用函数
   */
  def fun7(date: Date, log: String): Unit = {
    println(s"date is $date, log is $log")
  }

  def fun70 = fun7(new Date(), _: String)

  /**
   * 8.高阶函数
   */
  def fun8(a: Int, b: Int) = {
    a + b
  }

  def fun80(f: (Int, Int) => Int, s: String) = {
    val i = f(100, 200)
    i + "#" + s
  }

  def fun81(s: String) = {
    def fun811(a: String, b: String) = {
      s + "@" + a + "," + b
    }

    fun811 _
  }

  def fun82(f: (Int, Int) => Int): (String, String) => String = {
    val i = f(1, 2)
    (s1: String, s2: String) => s1 + " " + s2 + "*" + i
  }

  /**
   * 9.柯里化函数
   */
  def fun9(a: Int, b: Int)(c: Int, d: Int) = {
    a + b + c + d
  }


}
