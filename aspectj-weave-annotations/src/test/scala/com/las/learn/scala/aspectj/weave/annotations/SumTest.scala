package com.las.learn.scala.aspectj.weave.annotations

import org.scalatest.funspec.AnyFunSpec

/** author: liansheng
  */
class SumTest extends AnyFunSpec {
  describe("Sum of 2 and 3") {
    it("be 5") {
      val sum = new Sum
      assert(sum.checkSum(2, 3) == 5)
    }
  }
}
