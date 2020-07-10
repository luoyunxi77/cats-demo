package com.tw.monadsTransformers

import scala.concurrent.Future
import cats.data.{EitherT, OptionT}
import cats.syntax.applicative._

import cats.instances.future._ // for Monad
import scala.concurrent.Await
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration._

object ThreeMonadsTest extends App {
  type FutureEither[A] = EitherT[Future, String, A]
  type FutureEitherOption[A] = OptionT[FutureEither, A]

  val futureEitherOr: FutureEitherOption[Int] =
    for {
      a <- 10.pure[FutureEitherOption]
      b <- 32.pure[FutureEitherOption]
    } yield a + b

  println(futureEitherOr)
  // res6: FutureEitherOption[Int] = OptionT(EitherT(Future(Success(Right(Some(42))))) )

  val intermediate = futureEitherOr.value
  println(intermediate)
  // intermediate: FutureEither[Option[Int]] = EitherT(Future(Success(Right(Some(42)))))

  val stack = intermediate.value
  // stack: Future[Either[String, Option[Int]]] = Future(Success(Right(Some(42))))
  println(Await.result(stack, 1.second))
}
