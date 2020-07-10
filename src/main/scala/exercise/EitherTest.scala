package exercise

import cats.implicits._

object EitherTest extends App{
  val right: Either[String, Int] = Right(41)
  println(right.map(_ + 1) )

  val left: Either[String, Int] = Left("Hello")
  println(left.map(_ + 1) )
  println(left.leftMap(_.reverse))

  val right2: Either[String, Int] = 42.asRight[String]
  println(right2)
}
