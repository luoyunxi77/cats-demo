package com.tw.monads

object WriterTest extends App {
  def slowly[A](body: => A) =
    try body finally Thread.sleep(1000)

  def factorial(n: Int): Int = {
    val ans = slowly(if (n == 0) 1 else n * factorial(n - 1))
    println(s"fact $n $ans")
    ans
  }

  factorial(5)
}

import cats.data.Writer
import cats.instances.vector._
import cats.syntax.applicative._
import cats.syntax.writer._

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent._
import scala.concurrent.duration._

object WriterTest2 extends App {
  type Logged[A] = Writer[Vector[String], A]

  def slowly[A](body: => A) =
    try body finally Thread.sleep(1000)

  def factorial(n: Int): Logged[Int] =
    for {
      ans <- if (n == 0) {
        1.pure[Logged]
      } else {
        slowly(factorial(n - 1).map(_ * n))
      }
      _ <- Vector(s"fact $n $ans").tell
    } yield ans

  val (log, res) = factorial(5).run
  println(log)
  println(res)

  val result = Await.result(Future.sequence(Vector(
    Future(factorial(3)),
    Future(factorial(3))
  )), 5.seconds)

  println(result)
  // Vector(WriterT((Vector(fact 0 1, fact 1 1, fact 2 2, fact 3 6),6)), WriterT((Vector(fact 0 1, fact 1 1, fact 2 2, fact 3 6),6)))
}
