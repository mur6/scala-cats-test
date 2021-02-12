package example

import scala.util.{Failure, Success, Try}

// Tryでwrapすれば、getOrElseで失敗時の値を設定したり、recoverで例外のハンドリングができる。

object TryTest01 extends App {
  def divide(x: Int, y: Int) = Try { x / y }

  val result = divide(0, 0).getOrElse(-1)
  println(result)

  val result2 = divide(11, 2).get
  println(result2)

  divide(0, 0) match {
    case Success(i) => println(i)
    case Failure(e) => println(e.getMessage)
  }

  // foreachで成功時だけ処理を行わせる。
  // 下記のケースでは前者は例外にならないので結果が標準出力され、後者は例外が発生しているので何も出力されない。
  divide(3, 1) foreach println
  divide(0, 0) foreach println
}
