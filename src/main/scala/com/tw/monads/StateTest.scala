package com.tw.monads

import cats.data.State

object StateTest extends App {
  val a = State[Int, String] { state =>
    (state, s"The state is $state")
  }

  // Get the state and the result:
  val (state, result) = a.run(10).value
  // state: Int = 10
  // result: String = "The state is 10"

  // Get the state, ignore the result:
  val justTheState = a.runS(10).value
  // justTheState: Int = 10

  // Get the result, ignore the state:
  val justTheResult = a.runA(10).value
  // justTheResult: String = "The state is 10"
}
