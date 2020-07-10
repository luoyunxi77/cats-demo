package com.tw

import cats.Eq
import cats.syntax.eq._
import cats.instances.int._
import cats.instances.string._

final case class Cat(name: String, age: Int, color: String)

object CatsInstances {
  implicit val catEqual: Eq[Cat] =
    Eq.instance[Cat] { (cat1, cat2) =>
      (cat1.name === cat2.name) &&
        (cat1.age === cat2.age) &&
        (cat1.color === cat2.color)
    }
}

//import CatsInstances._
//import cats.instances.option._

object EqTest {
  def main(args: Array[String]): Unit = {
//    val cat1 = Cat("Garfield", 38, "orange and black")
//    // cat1: Cat = Cat("Garfield", 38, "orange and black")
//    val cat2 = Cat("Heathcliff", 32, "orange and black")
//    // cat2: Cat = Cat("Heathcliff", 32, "orange and black")
//    cat1 === cat2
//    // res15: Boolean = false
//    cat1 =!= cat2
//    // res16: Boolean = true
//
//    //option
//    val optionCat1 = Option(cat1)
//    // optionCat1: Option[Cat] = Some(Cat("Garfield", 38, "orange andblack"))
//    val optionCat2 = Option.empty[Cat]
    // optionCat2: Option[Cat] = None
//    optionCat1 === optionCat2
    // res17: Boolean = false
//    optionCat1 =!= optionCat2
    // res18: Boolean = true
  }
}
