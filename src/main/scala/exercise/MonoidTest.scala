package exercise

import cats.Monoid
import cats.implicits._

object MonoidTest extends App {
  println(Monoid[String].empty)
  println(Monoid[String].combineAll(List("a", "b", "c")))
  println(Monoid[String].combineAll(List()))


  val l = List(1, 2, 3, 4, 5)
  println(l.foldMap(identity))


//  implicit def monoidTuple[A: Monoid, B: Monoid]: Monoid[(A, B)] =
//    new Monoid[(A, B)] {
//      def combine(x: (A, B), y: (A, B)): (A, B) = {
//        val (xa, xb) = x
//        val (ya, yb) = y
//        (Monoid[A].combine(xa, ya), Monoid[B].combine(xb, yb))
//      }
//      def empty: (A, B) = (Monoid[A].empty, Monoid[B].empty)
//    }
  println(l.foldMap(i => (i, i.toString)))    //(15,"12345")
}
