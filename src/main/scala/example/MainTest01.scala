package example

import cats.Monoid
import cats._
import cats.data._
import cats.implicits._
import cats.data.Validated
import cats.data.Validated.{Invalid, Valid}

object MainTest01 extends App {
  for(i <- 1 to 5) println(i)
  println("MainTest01: Hello, World!")
}
