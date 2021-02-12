package example

import scala.util.Try

object EitherT01 extends App {
  def parseDouble(s: String): Either[String, Double] =
    Try(s.toDouble).map(Right(_)).getOrElse(Left(s"$s is not a number"))

  def divide(a: Double, b: Double): Either[String, Double] =
    Either.cond(b != 0, a / b, "Cannot divide by zero")

  def divisionProgram(inputA: String, inputB: String): Either[String, Double] =
    for {
      a <- parseDouble(inputA)
      b <- parseDouble(inputB)
      result <- divide(a, b)
    } yield result

  val ans1 = divisionProgram("4", "2")
  println(ans1) // Right(2.0)
  val ans2 = divisionProgram("a", "b")
  println(ans2) // Left("a is not a number")
}
