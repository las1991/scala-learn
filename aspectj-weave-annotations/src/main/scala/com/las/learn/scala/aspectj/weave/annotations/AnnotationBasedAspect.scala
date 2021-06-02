package com.las.learn.scala.aspectj.weave.annotations

import org.aspectj.lang.ProceedingJoinPoint
import org.aspectj.lang.annotation.{Around, Aspect}

@Aspect
class AnnotationBasedAspect {

  /** around execution of Sum
    */
//  @Around("execution(@com.las.learn.scala.aspectj.Loggable * *.*(..))")
//  def aroundSum(joinPoint: ProceedingJoinPoint): Object = {
//    val result = joinPoint.proceed
//    println(
//      "Method:-" + joinPoint
//        .toShortString() + " Input:-" + joinPoint.getArgs().toList.mkString(",") + " Result:-" + result
//    )
//    result
//  }

  @Around("execution(* com.las.learn.scala.aspectj.weave.annotations.Sum.checkSum(..))")
  def aroundSum1(joinPoint: ProceedingJoinPoint): Object = {
    joinPoint.getArgs
    val result = joinPoint.proceed
    println(s"Method=${joinPoint.toShortString()}, Input=${joinPoint.getArgs().toList.mkString(",")}, Result=${result}")
    result
  }

}
