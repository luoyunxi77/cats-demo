package com.tw

import cats.Monoid


object BooleanMonoidInstance {
  implicit val booleanAndMonoid: Monoid[Boolean] =
    new Monoid[Boolean] {
      def combine(a: Boolean, b: Boolean) = a && b

      def empty = true
    }

  implicit val booleanOrMonoid: Monoid[Boolean] =
    new Monoid[Boolean] {
      def combine(a: Boolean, b: Boolean) = a || b

      def empty = false
    }
}

import BooleanMonoidInstance._
import cats.Monoid
//import cats.syntax.semigroup._
//import cats.instances.option._

object MonoidTest {
  def main(args: Array[String]): Unit = {
    val andMonoid = Monoid[Boolean](booleanAndMonoid)
    println(andMonoid.combine(true, true))
//
//    def add[A: Monoid](items: List[A]): A =
//      items.foldLeft(Monoid[A].empty)(_ |+| _)

//    println(add(List(Some(1), None, Some(2), None, Some(3))))
  }
}
