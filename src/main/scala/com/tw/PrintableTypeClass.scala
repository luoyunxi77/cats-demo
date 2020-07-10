package com.tw

trait Printable[A] {
  def format(value: A): String
}

case class Cat(name: String, age: Int, color: String)

object PrintableInstances {
  implicit val stringPrintable = new Printable[String] {
    def format(input: String) = input
  }
  implicit val intPrintable = new Printable[Int] {
    def format(input: Int) = input.toString
  }

  implicit val catPrintable = new Printable[Cat] {
    def format(cat: Cat) = {
      //        val name = Printable.format(cat.name)
      //        val age = Printable.format(cat.age)
      //        val color = Printable.format(cat.color)
      s"${cat.name} is a ${cat.age} year-old ${cat.color} cat."
    }
  }
}

object PrintableSyntax {

  implicit class PrintableOps[A](value: A) {
    def format(implicit p: Printable[A]): String =
      p.format(value)

    def print(implicit p: Printable[A]): Unit =
      println(format(p))
  }

}

import PrintableSyntax._
import PrintableInstances._

object PrintableTypeClass {

  def main(args: Array[String]): Unit = {
    Cat("Garfield", 38, "ginger and black").print
    "hello".print
    11.print
  }
}
