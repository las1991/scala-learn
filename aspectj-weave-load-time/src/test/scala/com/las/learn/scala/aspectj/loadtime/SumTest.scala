package com.las.learn.scala.aspectj.loadtime

import org.scalatest.funspec.AnyFunSpec

class SumTest extends AnyFunSpec {
  describe("Sum of 2 and 3") {
    it("be 5") {
      val sum = new Sum
      assert(sum.checkSum(2, 3) == 5)
    }
    it("be 6") {
      val sum = new Sum
      assert(sum.checkSum(2, 3) == 6)
    }
  }
}
