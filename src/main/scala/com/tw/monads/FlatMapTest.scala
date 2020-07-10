package com.tw.monads

object FlatMapTest {
  //  def parseInt(str: String): Option[Int] = scala.util.Try(str.toInt).toOption

  def divide(a: Int, b: Int): Option[Int] = if (b == 0) None else Some(a / b)

  def stringDivideBy(aStr: String, bStr: String): Option[Int] =
    parseInt(aStr).flatMap { aNum =>
      parseInt(bStr).flatMap { bNum =>
        divide(aNum, bNum)
      }
    }

  def parseInt(str: String): Option[Int] = scala.util.Try(str.toInt).toOption

  def stringDivideBy2(aStr: String, bStr: String): Option[Int] = for {
    aNum <- parseInt(aStr)
    bNum <- parseInt(bStr)
    ans <- divide(aNum, bNum)
  } yield ans

  def main(args: Array[String]): Unit = {
    println(stringDivideBy("12", "2"))
    println(stringDivideBy("12", "0"))
    println(stringDivideBy("12", "str"))
  }
}


