package com.tw.functor

import cats.Functor
import cats.instances.function._
import cats.instances.list._
import cats.instances.option._
import cats.syntax.functor._ // for Functor

object FunctorTest {

  def doMath[F[_]](start: F[Int])
                  (implicit functor: Functor[F]): F[Int] =
    start.map(n => n + 1 * 2)

  def main(args: Array[String]): Unit = {

    val func1 = (a: Int) => a + 1
    val func2 = (a: Int) => a * 2
    val func3 = (a: Int) => s"${a}!"
    val func4 = func1.map(func2).map(func3)
    println(func4(123))

    println(doMath(Option(20)))
    println(doMath(List(20,40)))
  }
}
