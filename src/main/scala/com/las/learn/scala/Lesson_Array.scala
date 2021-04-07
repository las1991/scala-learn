package com.las.learn.scala

import scala.collection.mutable.ArrayBuffer

/**
 * @auther: liansheng
 * @Date: 2021/3/30 15:19
 * @Description:
 */
object Lesson_Array {

  def main(args: Array[String]): Unit = {
    val arr = Array[String]("a", "b", "c", "d");
    arr.foreach(println(_))
    for (elem <- arr) {
      println(elem)
    }

    val arr1 = new Array[Int](3)
    arr1(0) = 100
    arr1(1) = 200
    arr1(2) = 300

    val arr2 = new Array[Array[Int]](3)
    arr2(0) = Array[Int](1, 2, 3)
    arr2(1) = Array[Int](4, 5, 6)
    arr2(2) = Array[Int](7, 8, 9)
    arr2.foreach(e => e.foreach(println(_)))

    val arr3 = Array[Int](1, 2, 3)
    val arr4 = Array[Int](4, 5, 6)
    Array.concat(arr3, arr4)

    val arr5: Array[String] = Array.fill(5)("hello")

    val arr6 = ArrayBuffer[Int](1, 2, 3)
    arr6.+=(4)
    arr6.append(5)
  }

}
