package com.las.learn.scala.aspectj

/** author: liansheng
  */

object Sum extends App {
  val sum = new Sum
  sum.checkSum(2, 3)
}

class Sum {
  @Loggable
  def checkSum(a: Int, b: Int): Int = a + b
}
