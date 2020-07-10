package exercise

import cats.Foldable
//import cats.instances.list._
//import cats.instances.option._
import cats.implicits._

object FoldableTest extends App {
  //foldK
  println(Foldable[List].foldK(List(List(1, 2), List(3, 4, 5))))
  // List(1, 2, 3, 4, 5)

  println(Foldable[List].foldK(List(None, Option("two"), Option("three"))))
  //Some(two)
  println(Foldable[List].foldK(List(Option("one"), Option("two"), Option("three"))))
  //Some(one)

  println(Foldable[List].find(List(1, 2, 3))(_ > 2))

  println(Foldable[List].exists(List(1, 2, 3))(_ > 2))

  def parseInt(s: String): Option[Int] =
    Either.catchOnly[NumberFormatException](s.toInt).toOption

  println(Foldable[List].traverse_(List("1", "2", "3"))(parseInt))
  println(Foldable[List].traverse_(List("a", "b", "c"))(parseInt))

  val FoldableListOption = Foldable[List].compose[Option]
  println(FoldableListOption.fold(List(Option(1), Option(2), Option(3), Option(4))))
  println(FoldableListOption.fold(List(Option("1"), Option("2"), None, Option("3"))))

  println(Foldable[List].isEmpty(List(1, 2, 3)))
  println(Foldable[List].dropWhile_(List(1, 2, 3))(_ < 2))
  println(Foldable[List].takeWhile_(List(1, 2, 3))(_ < 2))
}
