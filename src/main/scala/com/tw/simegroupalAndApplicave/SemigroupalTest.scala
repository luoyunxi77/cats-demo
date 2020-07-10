package com.tw.simegroupalAndApplicave

import cats.Semigroupal
import cats.instances.list._
import cats.kernel.Semigroup

object SemigroupalTest extends App {

  println(Semigroupal[List].product(List(1, 2), List(3, 4)))
  //  List((1,3), (1,4), (2,3), (2,4))

  println(Semigroup[List[Int]].combine(List(1, 2, 3), List(4, 5, 6)))
  //  List(1, 2, 3, 4, 5, 6)
}

import cats.syntax.apply._
import cats.syntax.validated._
import cats.instances.vector._ // for Semigroupal

object ValidatedTest extends App {
  val result = (
    List(1, 2).valid[List[Int]],
    List(3, 4).valid[List[Int]]
    ).tupled
  println(result) //Valid((List(1, 2),List(3, 4)))

  val result2 = (
    List(1, 2).invalid[List[Int]],
    List(3, 4).invalid[List[Int]]
    ).tupled
  println(result2) //Invalid(List(1, 2, 3, 4))

  val result3 = (
    Vector(404).invalid[Int],
    Vector(500).invalid[Int]
    ).tupled
  println(result3) //Invalid(Vector(404, 500))

  println("fail".invalid[Int].fold(_ + "!!!", _.toString))
}
