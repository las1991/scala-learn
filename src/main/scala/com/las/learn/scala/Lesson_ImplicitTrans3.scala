package com.las.learn.scala

/**
 * @auther: liansheng
 * @Date: 2021/3/31 14:36
 * @Description:
 */

class Dog(xname: String) {
  val name = xname
}

object Lesson_ImplicitTrans3 {

  implicit class Cat(d: Dog) {
    def showName() = {
      println(s"${d.name} is dog")
    }
  }

  def main(args: Array[String]): Unit = {
    val dog = new Dog("tom")
    dog.showName()
  }

}
