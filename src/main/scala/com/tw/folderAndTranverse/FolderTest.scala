package com.tw.folderAndTranverse

object FolderTest extends App {
  val result = List(1, 2, 3).foldLeft(List.empty[Int])((a, i) => i :: a)
  println(result)

  def map[A, B](list: List[A])(func: A => B): List[B] =
    list.foldRight(List.empty[B]) { (item, accum) =>
      func(item) :: accum
    }

  println(map(List(1, 2, 3))(_ * 2))

  def flatMap[A, B](list: List[A])(func: A => List[B]): List[B] =
    list.foldRight(List.empty[B]) { (item, accum) =>
      func(item) ::: accum
    }

  println(flatMap(List(1, 2, 3))(a => List(a, a * 10, a * 100)))

  def filter[A](list: List[A])(func: A => Boolean): List[A] =
    list.foldRight(List.empty[A]) { (item, accum) =>
      if (func(item)) item :: accum else accum
    }

  println(filter(List(1, 2, 3))(_ % 2 == 1))
}

import cats.Foldable
import cats.instances.list._ // for Foldable
object CatsFoldableTest extends App {
  val ints = List(1, 2, 3)
  val result = Foldable[List].foldLeft(ints, 0)(_ + _)
  println(result)

  def bigData = (1 to 100000).to(LazyList)
  val result2 = bigData.foldRight(0L)(_ + _)
  println(result2)
}
