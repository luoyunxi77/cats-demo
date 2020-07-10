package exercise

import cats.Monad
import cats.instances.list._
import cats.instances.option._

object MonadTest extends App {
  println(Option(Option(1)).flatten)
  println(Option(None))
  println(List(List(1), List(2, 3)).flatten)

  println(Monad[List].flatMap(List(1, 2, 3))(x => List(x, x)))

  println(Monad[Option].ifM(Option(true))(Option("truthy"), Option("falsy")))
  //Some(truthy)
  println(Monad[Option].ifM(Option(false))(None, Option("falsy")))
  //Option("falsy")
  println(Monad[List].ifM(List(true, false, true))(List(1, 2), List(3, 4)))
  // List(1, 2, 3, 4, 1, 2)
}

object MonadCompositionTest extends App {

  case class OptionT[F[_], A](value: F[Option[A]])

  implicit def optionTMonad[F[_]](implicit F: Monad[F]) = {
    new Monad[OptionT[F, *]] {
      def pure[A](a: A): OptionT[F, A] = OptionT(F.pure(Some(a)))

      def flatMap[A, B](fa: OptionT[F, A])(f: A => OptionT[F, B]): OptionT[F, B] =
        OptionT {
          F.flatMap(fa.value) {
            case None => F.pure(None)
            case Some(a) => f(a).value
          }
        }

      def tailRecM[A, B](a: A)(f: A => OptionT[F, Either[A, B]]): OptionT[F, B] =
        tailRecM(a)(f)
    }
  }

  println(optionTMonad[List].pure(42)) //OptionT(List(Some(42)))
}
