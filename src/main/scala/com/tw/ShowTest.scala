package com.tw

object ShowTest {

  import cats.Show
  import cats.implicits._

  def main(args: Array[String]): Unit = {
    val showInt = Show.apply[Int]
    val showString: Show[String] = Show.apply[String]

    val intAsString: String = showInt.show(123)
    val stringAsString: String = showString.show("abc")

    println(intAsString)
    println(stringAsString)
  }
}

object ShowTest2 {

  import cats.syntax.show._
  import cats.instances.int._
  import cats.instances.string._

  def main(args: Array[String]): Unit = {
    val intAsString = 123.show

    val stringAsString = "abc".show

    println(intAsString)
    println(stringAsString)
  }
}


import cats.Show
import cats.implicits._
import CatsInstancesShow._

object CatsInstancesShow {
  implicit val catShow: Show[Cat] = Show.show[Cat] { cat =>
    val name = cat.name.show
    val age = cat.age.show
    val color = cat.color.show
    s"$name is a $age year-old $color cat."
  }
}

object ShowTest3{
  def main(args: Array[String]): Unit = {
    println(Cat("Garfield", 38, "ginger and black").show)
  }
}
