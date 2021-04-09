package com.las.learn.scala.bjsxt_lesson

/**
 * @auther: liansheng
 * @Date: 2021/3/31 10:25
 * @Description:
 */
trait Listen {
  def listen(string: String) = {
    println(string)
  }
}

trait Read {
  def read(str: String) = {
    println(str)
  }
}

class Human extends Listen with Read {

}

object Lesson_Trait1 {

  def main(args: Array[String]): Unit = {
    val human = new Human()
    human.listen("a");
    human.read("b")
  }

}
