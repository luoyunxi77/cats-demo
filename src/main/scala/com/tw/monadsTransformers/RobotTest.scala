package com.tw.monadsTransformers

import cats.data.EitherT

import scala.concurrent.{Await, Future}
import cats.instances.future._
import scala.concurrent.duration._

import scala.concurrent.ExecutionContext.Implicits.global

object RobotTest extends App {
  type Response[A] = EitherT[Future, String, A]

  val powerLevels = Map(
    "Jazz" -> 6,
    "Bumblebee" -> 8,
    "Hot Rod" -> 10
  )

  def getPowerLevel(ally: String): Response[Int] = {
    powerLevels.get(ally) match {
      case Some(avg) => EitherT.right(Future(avg))
      case None => EitherT.left(Future(s"$ally unreachable"))
    }
  }

  def canSpecialMove(ally1: String, ally2: String): Response[Boolean] =
    for {
      power1 <- getPowerLevel(ally1)
      power2 <- getPowerLevel(ally2)
    } yield (power1 + power2) > 15

  def tacticalReport(ally1: String, ally2: String): String = {
    val stack = canSpecialMove(ally1, ally2).value
    Await.result(stack, 1.second) match {
      case Left(msg) =>
        s"Comms error: $msg"
      case Right(true) =>
        s"$ally1 and $ally2 are ready to roll out!"
      case Right(false) =>
        s"$ally1 and $ally2 need a recharge."
    }
  }

  println(tacticalReport("Jazz", "Bumblebee")) // Jazz and Bumblebee need a recharge.

  println(tacticalReport("Bumblebee", "Hot Rod")) // Bumblebee and Hot Rod are ready to roll out!

  println(tacticalReport("Jazz", "Ironhide")) //Comms error: Ironhide unreachable
}
