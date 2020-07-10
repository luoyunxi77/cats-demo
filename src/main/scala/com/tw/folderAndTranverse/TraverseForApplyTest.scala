package com.tw.folderAndTranverse

import cats.Applicative
import cats.data.Validated
import cats.syntax.apply._
import cats.syntax.applicative._
import cats.instances.vector._
import cats.instances.option._ // for Applicative

object TraverseForApplyTest extends App {
  def listTraverse[F[_] : Applicative, A, B](list: List[A])(func: A => F[B]): F[List[B]] =
    list.foldLeft(List.empty[B].pure[F]) {
      (accum, item) => (accum, func(item)).mapN(_ :+ _)
    }

  def listSequence[F[_] : Applicative, B](list: List[F[B]]): F[List[B]] =
    listTraverse(list)(identity)

  println(listSequence(List(Vector(1, 2), Vector(3, 4))))
  //Vector(List(1, 3), List(1, 4), List(2, 3), List(2, 4))

  println(listSequence(List(Vector(1, 2), Vector(3, 4), Vector(5, 6))))
  //Vector(List(1, 3, 5), List(1, 3, 6), List(1, 4, 5), List(1, 4, 6), List(2, 3, 5), List(2, 3, 6), List(2, 4, 5), List(2, 4, 6))

  def processOption(inputs: List[Int]) =
    listTraverse(inputs)(n => if (n % 2 == 0) Some(n) else None)

  println(processOption(List(2, 4, 6)))
  println(processOption(List(1, 2, 3)))

  import cats.instances.list._ // for Monoid
  type ErrorsOr[A] = Validated[List[String], A]
  def process(inputs: List[Int]): ErrorsOr[List[Int]] = listTraverse(inputs) { n =>
    if(n % 2 == 0) {
      Validated.valid(n)
    } else {
      Validated.invalid(List(s"$n is not even"))
    } }

  println(process(List(2, 4, 6)))
  println(process(List(1, 2, 3)))
}
