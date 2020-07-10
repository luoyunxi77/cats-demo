package exercise

import cats.{Applicative, Id}
import cats.data.{Validated, ValidatedNel}
import cats.implicits._

object TraverseTest extends App {

  def parseIntEither(s: String): Either[NumberFormatException, Int] =
    Either.catchOnly[NumberFormatException](s.toInt)

  def parseIntValidated(s: String): ValidatedNel[NumberFormatException, Int] =
    Validated.catchOnly[NumberFormatException](s.toInt).toValidatedNel

  println(List("1", "2", "3").traverse(parseIntEither))
  println(List("1", "abc", "3").traverse(parseIntEither).isLeft)

  println(List(Option(1), Option(2), Option(3)).traverse(identity))
  println(List(Option(1), Option(2), Option(3)).sequence)

  println(List(Option(1), Option(2), Option(3)).sequence_)
  println(List(Option(1), None, Option(3)).sequence_ )

  println(Applicative[Id].pure(42))
}
