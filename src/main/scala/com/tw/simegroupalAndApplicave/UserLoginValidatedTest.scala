package com.tw.simegroupalAndApplicave

import cats.data.Validated
import cats.syntax.either._ // for catchOnly

import cats.instances.list._ // for Semigroupal
import cats.syntax.apply._ // for mapN

object UserLoginValidatedTest extends App {

  case class User(name: String, age: Int)

  type FormData = Map[String, String]
  type FailFast[A] = Either[List[String], A]
  type FailSlow[A] = Validated[List[String], A]

  def getValue(name: String)(data: FormData): FailFast[String] =
    data.get(name).toRight(List(s"$name field not specified"))

  val getName = getValue("name") _

  def parseInt(name: String)(data: String): FailFast[Int] =
    Either.catchOnly[NumberFormatException](data.toInt).leftMap(_ => List(s"$name must be an integer"))

  def nonBlank(name: String)(data: String): FailFast[String] =
    Right(data).ensure(List(s"$name cannot be blank"))(_.nonEmpty)

  def nonNegative(name: String)(data: Int): FailFast[Int] =
    Right(data).ensure(List(s"$name must be non-negative"))(_ >= 0)

  def readName(data: FormData): FailFast[String] =
    getValue("name")(data).
      flatMap(nonBlank("name"))

  def readAge(data: FormData): FailFast[Int] =
    getValue("age")(data).
      flatMap(nonBlank("age")).
      flatMap(parseInt("age")).
      flatMap(nonNegative("age"))

  def readUser(data: FormData): FailSlow[User] =
    (
      readName(data).toValidated,
      readAge(data).toValidated
      ).mapN(User.apply)

  println(readUser(Map("name" -> "Dave", "age" -> "37")))
  println(readUser(Map("age" -> "-1")))

  //  println(getName(Map("name" -> "lisa")))
  //  println(getName(Map()))
  //
  //  println(parseInt("age")("20"))
  //  println(parseInt("age")("foo"))
  //
  //  println(nonBlank("name")("Dade Murphy"))
  //  println(nonBlank("name")(""))

  //  println(readName(Map("name" -> "Dade Murphy")))
  //  println(readName(Map("name" -> "")))
}
