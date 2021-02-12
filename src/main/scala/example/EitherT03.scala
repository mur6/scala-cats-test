package example

import cats.data.EitherT
// import cats.implicits._
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.{Await, Future}
import scala.util.Try

object EitherT03 extends App {
  def parseDouble(s: String): Either[String, Double] =
    Try(s.toDouble).map(Right(_)).getOrElse(Left(s"$s is not a number"))

  def parseDoubleAsync(s: String): Future[Either[String, Double]] =
    Future.successful(parseDouble(s))

  def divide(a: Double, b: Double): Either[String, Double] =
    Either.cond(b != 0, a / b, "Cannot divide by zero")

  def divideAsync(a: Double, b: Double): Future[Either[String, Double]] =
    Future.successful(divide(a, b))

  def divisionProgramAsync(inputA: String, inputB: String): EitherT[Future, String, Double] =
    for {
      a <- EitherT(parseDoubleAsync(inputA))
      b <- EitherT(parseDoubleAsync(inputB))
      result <- EitherT(divideAsync(a, b))
    } yield result

  val ans1 = divisionProgramAsync("4", "2").value
  val ans2 = divisionProgramAsync("a", "b").value
  Thread.sleep(5000)
  println(ans1) // Future(Success(Right(2.0)))
  println(ans2) // Future(Success(Left(a is not a number)))
}
