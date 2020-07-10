package com.tw.monads

import cats.Monad
import cats.instances.list._

object MonadsTest {
  def main(args: Array[String]): Unit = {
    val list1 = Monad[List].pure(3)

    val list2 = Monad[List].flatMap(List(1, 2, 3))(a => List(a, a * 10))

    val list3 = Monad[List].map(List(1, 2, 3))(a => a + 123)

    println(list1)
    println(list2)
    println(list3)

    import cats.instances.future._
    import scala.concurrent.ExecutionContext.Implicits.global
    import scala.concurrent._
    import scala.concurrent.duration._ //for second
    val fm = Monad[Future]
    val future = fm.flatMap(fm.pure(1))(a => fm.pure(a + 2))
    println(future)
    println(Await.result(future, 1.second))

    import cats.syntax.applicative._
    val result = 3.pure[List]
    println(result)
  }
}

