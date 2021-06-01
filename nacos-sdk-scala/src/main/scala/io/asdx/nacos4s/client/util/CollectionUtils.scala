package io.asdx.nacos4s.client.util

/**
 * @auther: liansheng
 * @Date: 2021/5/19 10:16
 * @Description:
 */
object CollectionUtils {
  implicit class ToJavaList[T](val iterable: Iterable[T]) extends AnyVal {
    def asJavaList: java.util.List[T] = {
      val list = new java.util.ArrayList[T](iterable.size)
      iterable.foreach(list.add)
      list
    }
  }
}
