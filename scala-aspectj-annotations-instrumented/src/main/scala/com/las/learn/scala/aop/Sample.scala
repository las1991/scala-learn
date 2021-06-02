package com.las.learn.scala.aop

/** @author: liansheng
  * @Description:
  */

class Sample {
  def printSample() = println("hello")
}

object Sample extends App {
  val sample = new Sample
  sample.printSample()
}
