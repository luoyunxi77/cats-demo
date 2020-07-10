package com.tw.monads

import cats.data.Reader

object ReaderTest extends App {

  case class Cat(name: String, favoriteFood: String)

  val catName: Reader[Cat, String] = Reader(cat => cat.name)
  println(catName.run(Cat("Garfield", "lasagne"))) //Garfield

  val greetKitty: Reader[Cat, String] = catName.map(name => s"Hello ${name}")
  println(greetKitty.run(Cat("Heathcliff", "junk food")))

  val feedKitty: Reader[Cat, String] = Reader(cat => s"Have a nice bowl of ${cat.favoriteFood}")
  val greetAndFeed: Reader[Cat, String] =
    for {
      greet <- greetKitty
      feed <- feedKitty
    } yield s"$greet. $feed."
  println(greetAndFeed(Cat("Garfield", "lasagne")))

}
