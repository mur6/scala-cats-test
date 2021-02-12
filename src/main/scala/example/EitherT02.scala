package example

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration.Duration
import scala.concurrent.{Await, Future}
import scala.util.Try

object EitherT02 extends App {
  def parseDouble(s: String): Either[String, Double] =
    Try(s.toDouble).map(Right(_)).getOrElse(Left(s"$s is not a number"))

  def parseDoubleAsync(s: String): Future[Either[String, Double]] =
    Future.successful(parseDouble(s))

  def divide(a: Double, b: Double): Either[String, Double] =
    Either.cond(b != 0, a / b, "Cannot divide by zero")

  def divideAsync(a: Double, b: Double): Future[Either[String, Double]] =
    Future.successful(divide(a, b))

  def divisionProgramAsync(inputA: String, inputB: String): Future[Either[String, Double]] =
    parseDoubleAsync(inputA) flatMap { eitherA =>
      parseDoubleAsync(inputB) flatMap { eitherB =>
        (eitherA, eitherB) match {
          case (Right(a), Right(b)) => divideAsync(a, b)
          case (Left(err), _) => Future.successful(Left(err))
          case (_, Left(err)) => Future.successful(Left(err))
        }
      }
    }

  val ans1 = Await.result(divisionProgramAsync("4", "2"), Duration.Inf)
  println(ans1) // Right(2.0)
  val ans2 = Await.result(divisionProgramAsync("a", "b"), Duration.Inf)
  println(ans2) // Left("a is not a number")
}
