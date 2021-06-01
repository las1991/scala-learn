package bjsxt_lesson

/** @auther: liansheng
  * @Date: 2021/3/18 16:35
  * @Description:
  */

class Person(val xname: String, xage: Int) {
  var name         = xname + "1";
  var age          = xage;
  private var addr = "1111"

  println("***************")

  def this() {
    this("1", 1)
  }

  println("===============")
}

object Lesson_ClassAndObj {
  println("################")

  def apply(score: Int) {
    println("score :" + score)
  }

  def main(args: Array[String]): Unit = {
    Lesson_ClassAndObj(100)
    val p = new Person("tom", 19)
    println(p.name)
    println(p.xname)

    /** if ... else ...
      */
    if (p.age < 20) {
      println("<20")
    } else if (p.age == 20) {
      println("=20")
    } else {
      println("<20")
    }

    /** for
      */
    var r  = 1 to 10
    var r1 = 1 until 10
    for (i <- 1 to 10) {
      for (j <- 1 to i)
        print(s"$i*$j=" + i * j + "\t")
      println()
    }

    val result =
      for (
        i <- 1 to 100 if i > 20
        if i % 2 == 0
      ) yield i
    println(result)
  }
}
