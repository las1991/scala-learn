package com.las.learn.scala.aspectj.loadtime

object Sum extends App {
  val sum = new Sum
  println(sum.checkSum(2, 3))
}

class Sum {
  def checkSum(a: Int, b: Int): Int = a + b
}
