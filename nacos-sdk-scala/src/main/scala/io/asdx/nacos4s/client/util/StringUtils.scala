package io.asdx.nacos4s.client.util

/**
 * @auther: liansheng
 * @Date: 2021/5/19 10:15
 * @Description:
 */
import scala.annotation.tailrec

object StringUtils {
  val BLACK_CHAR: Char = ' '

  def isEmpty(s: CharSequence): Boolean =
    (s eq null) || s.length() == 0

  @inline def isNoneEmpty(s: CharSequence): Boolean = !isEmpty(s)

  def isBlank(s: CharSequence): Boolean = {
    @tailrec def isNoneBlankChar(s: CharSequence, i: Int): Boolean =
      if (i < s.length()) s.charAt(i) != BLACK_CHAR || isNoneBlankChar(s, i + 1) else false

    isEmpty(s) || !isNoneBlankChar(s, 0)
  }

  @inline def isNoneBlank(s: CharSequence): Boolean = !isBlank(s)
}
