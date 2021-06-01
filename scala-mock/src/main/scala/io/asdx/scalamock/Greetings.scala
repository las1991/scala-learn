package io.asdx.scalamock

/**
 * @auther: liansheng
 * @Date: 2021/3/31 17:27
 * @Description:
 */
object Greetings {

  trait Formatter {
    def format(s: String): String

    def format1(s: String): String
  }

  object EnglishFormatter extends Formatter {
    override def format(s: String): String = s"Hello $s"

    override def format1(s: String): String = format(s) + "1111"
  }

  object GermanFormatter extends Formatter {
    override def format(s: String): String = s"Hallo $s"

    override def format1(s: String): String = format(s) + "1111"
  }

  object JapaneseFormatter extends Formatter {
    override def format(s: String): String = s"こんにちは $s"

    override def format1(s: String): String = format(s) + "1111"
  }

  def sayHello(name: String, formatter: Formatter): Unit = {
    println(formatter.format(name))
  }

  def sayHello1(name: String, formatter: Formatter): Unit = {
    println(formatter.format(name))
    println(formatter.format1(name))
  }

}
