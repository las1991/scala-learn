package io.asdx.nacos4s

import org.scalatest.concurrent.ScalaFutures
import org.scalatest.funspec.AnyFunSpecLike
import org.scalatest.matchers.should.Matchers
import org.scalatest.{EitherValues, OptionValues}

import scala.concurrent.duration.{FiniteDuration, _}

/**
 * @auther: liansheng
 * @Date: 2021/5/19 10:23
 * @Description:
 */
trait FusionFunSpecLike extends AnyFunSpecLike with FusionTestValues

trait FusionTestValues extends OptionValues with EitherValues with Matchers

trait FusionScalaFutures extends ScalaFutures {
  implicit override def patienceConfig: PatienceConfig = PatienceConfig(patienceTimeout, 15.millis)

  protected def patienceTimeout: FiniteDuration = 10.second
}