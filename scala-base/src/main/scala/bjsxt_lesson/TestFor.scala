package bjsxt_lesson

/** @auther: liansheng
  * @Date: 2021/4/30 16:01
  * @Description:
  */
object TestFor {

  def main(args: Array[String]): Unit = {
    val total = BigDecimal(100)
    var tmp   = BigDecimal(0)

    //    val res = collection.mutable.ListBuffer.empty[Int]
    val res = Range(1, 100)
      .foldLeft((0, Seq.empty[Int])) { case ((numRes, seqRes), v) =>
        if (numRes > 100) (numRes, seqRes) else (numRes + v, seqRes :+ v)
      }

    //    for (ele <- 1 to 100 if tmp < total) {
    //      tmp += ele
    //
    ////      res=res :+ ele
    //    }

    println(res)
  }

}
