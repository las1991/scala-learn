package com.las.learn.scala

/**
 * @auther: liansheng
 * @Date: 2021/3/31 14:29
 * @Description:
 */
class Animal(name: String) {
  def canFly() = {
    println(s"$name can fly ...")
  }
}

class Rabbit(xname: String) {
  val name = xname
}

object Lesson_ImplicitTrans2 {
  implicit def rabbit2Animal(r: Rabbit): Animal = {
    new Animal(r.name)
  }

  def main(args: Array[String]): Unit = {
    val rabbit = new Rabbit("rabbit")
    rabbit.canFly()
  }
}
