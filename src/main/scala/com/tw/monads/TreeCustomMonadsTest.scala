package com.tw.monads

import cats.Monad

import scala.annotation.tailrec
//import cats.syntax.functor._ // for map
import cats.syntax.flatMap._ // for flatMap

sealed trait Tree[+A]

final case class Branch[A](left: Tree[A], right: Tree[A])
  extends Tree[A]

final case class Leaf[A](value: A) extends Tree[A]

object TreeTest {
  def branch[A](left: Tree[A], right: Tree[A]): Tree[A] = Branch(left, right)

  def leaf[A](value: A): Tree[A] =
    Leaf(value)

  // 自定义monads
  //  implicit val treeMonad = new Monad[Tree] {
  //    def pure[A](value: A): Tree[A] = Leaf(value)
  //
  //    def flatMap[A, B](tree: Tree[A])(func: A => Tree[B]): Tree[B] =
  //      tree match {
  //        case Branch(l, r) =>
  //          Branch(flatMap(l)(func), flatMap(r)(func))
  //        case Leaf(value) =>
  //          func(value)
  //      }
  //
  //    override def tailRecM[A, B](a: A)(func: A => Tree[Either[A, B]]): Tree[B] =
  //      flatMap(func(a)) {
  //        case Left(value) =>
  //          tailRecM(value)(func)
  //        case Right(value) =>
  //          Leaf(value)
  //      }
  //  }

  implicit val treeMonad2 = new Monad[Tree] {
    def pure[A](value: A): Tree[A] = Leaf(value)

    def flatMap[A, B](tree: Tree[A])(func: A => Tree[B]): Tree[B] =
      tree match {
        case Branch(l, r) =>
          Branch(flatMap(l)(func), flatMap(r)(func))
        case Leaf(value) =>
          func(value)
      }

    def tailRecM[A, B](arg: A)(func: A => Tree[Either[A, B]]): Tree[B] = {
      @tailrec
      def loop(open: List[Tree[Either[A, B]]], closed: List[Option[Tree[B]]]): List[Tree[B]] =
        open match {
          case Branch(l, r) :: next =>
            loop(l :: r :: next, None :: closed)
          case Leaf(Left(value)) :: next =>
            loop(func(value) :: next, closed)
          case Leaf(Right(value)) :: next =>
            loop(next, Some(pure(value)) :: closed)
          case Nil =>
            closed.foldLeft(Nil: List[Tree[B]]) { (acc, maybeTree) =>
              maybeTree.map(_ :: acc).getOrElse {
                val left :: right :: tail = acc
                branch(left, right) :: tail
              }
            }
        }

      loop(List(func(arg)), Nil).head
    }
  }

  println(branch(leaf(100), leaf(200)).flatMap(x => branch(leaf(x - 1), leaf(x + 1))))
}
