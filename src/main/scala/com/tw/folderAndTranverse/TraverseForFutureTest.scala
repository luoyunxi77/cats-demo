package com.tw.folderAndTranverse

import scala.concurrent.{Await, Future}
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration._

object TraverseForFutureTest extends App {
  val hostnames = List(
    "alpha.example.com",
    "beta.example.com",
    "gamma.demo.com"
  )

  def getUptime(hostname: String): Future[Int] =
    Future(hostname.length * 60) // just for demonstration

  val allUptimes: Future[List[Int]] = hostnames.foldLeft(Future(List.empty[Int])) {
    (accum, host) =>
      val uptime = getUptime(host)
      for {
        accum <- accum
        uptime <- uptime
      } yield accum :+ uptime
  }

  val allUptimes2: Future[List[Int]] = Future.traverse(hostnames)(getUptime)

  println(Await.result(allUptimes, 1.second))
  println(Await.result(allUptimes2, 1.second))

}
