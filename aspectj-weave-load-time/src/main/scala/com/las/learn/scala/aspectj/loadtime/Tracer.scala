package com.las.learn.scala.aspectj.loadtime

import org.aspectj.lang.ProceedingJoinPoint
import org.aspectj.lang.annotation.{Around, Aspect}

@Aspect
class Tracer {

  @Around("execution(* com.las.learn.scala.aspectj.loadtime.Sum.checkSum(..))")
  def aroundSum1(joinPoint: ProceedingJoinPoint): Int = {
    val result = joinPoint.proceed
    println(s"Method=${joinPoint.toShortString()}, Input=${joinPoint.getArgs().toList.mkString(",")}, Result=${result}")
    (result.toString.toInt + 1)
  }
}
