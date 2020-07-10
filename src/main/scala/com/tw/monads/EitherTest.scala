package com.tw.monads

import cats.syntax.either._

object EitherTest extends App {
  val either1: Either[String, Int] = Right(10)
  val either2: Either[String, Int] = Right(32)
  val result = for {
    a <- either1
    b <- either2
  } yield a + b

  println(result)

  println(10.asRight[Int])


  def countPositive(nums: List[Int]) = nums.foldLeft(0.asRight[String]) { (accumulator, num) =>
    if (num > 0) {
      accumulator.map(_ + 1)
    } else {
      Left("Negative. Stopping!")
    }
  }

  //  println(countPositive(List(1, 2, 3))) //Right(3)
  println(countPositive(List(1, -2, 3))) //"Negative. Stopping!"

  println(Either.catchOnly[NumberFormatException]("foo".toInt))

  println(Either.fromTry(scala.util.Try("12".toInt)))
  println(Either.fromOption[String, Int](None, "Badness"))
}
