package bjsxt_lesson

/** @auther: liansheng
  * @Date: 2021/3/30 15:47
  * @Description:
  */
object Lesson_Set {

  def main(args: Array[String]): Unit = {
    val set    = Set[Int](1, 2, 3, 4, 5, 6, 6)
    val set1   = Set[Int](1, 2, 3, 7, 8, 9)
    val result = set.diff(set1)

    println(set & set1)

    val set2 = scala.collection.mutable.Set[Int](1, 2, 3, 4, 5)
    set2.+=(100)
    set2 += (1001)

  }

}
