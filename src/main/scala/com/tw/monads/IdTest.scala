package com.tw.monads

import cats.Id

object IdTest extends App {
  def pure[A](value: A): Id[A] = value

  def map[A, B](initial: Id[A])(func: A => B): Id[B] = func(initial)

  def flatMap[A, B](initial: Id[A])(func: A => Id[B]): Id[B] = func(initial)

  println(pure(123))
  println(map(123)(_ * 2))
  println(flatMap(123)(_ * 2))
}


import cats.Monad
import cats.syntax.flatMap._ // for flatMap
import cats.syntax.functor._ // for map
import cats.instances.list._  // for list
import cats.syntax.applicative._ // for pure

import cats.Id

object IdentityTest extends App {

  def sumSquare[F[_] : Monad](a: F[Int], b: F[Int]): F[Int] = for {
    x <- a
    y <- b
  } yield x * x + y * y

  def sumSquare1[F[_] : Monad](a: F[Int], b: F[Int]): F[Int] =
    a.flatMap(x => b.map(y => x * x + y * y))

  println(sumSquare(List(1, 2), List(3, 4)))
  println(sumSquare(1.pure[List],2.pure[List]))
  println(sumSquare(1: Id[Int], 2: Id[Int]))
}
