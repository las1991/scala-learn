package com.las.learn.scala.bjsxt_lesson

import java.util.Objects

/**
 * @auther: liansheng
 * @Date: 2021/3/31 10:51
 * @Description:
 */

case class Book(name: String) {

}

object Lesson_CaseClass {
  def main(args: Array[String]): Unit = {
    val book = new Book("java")
    val book1 = new Book("scala")
    val book2 = new Book("java")
    println(book.equals(book1))
    println(book.equals(book2)) //true
    println(Objects.equals(book, book1))
    println(Objects.equals(book, book2)) //true

  }
}
