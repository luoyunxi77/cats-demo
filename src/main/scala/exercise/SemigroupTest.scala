package exercise

import cats.kernel.Semigroup
import cats.implicits._

object SemigroupTest extends App {
  val aMap = Map("foo" -> Map("bar" -> 5))
  val anotherMap = Map("foo" -> Map("bar" -> 6))
  val combinedMap = Semigroup[Map[String, Map[String, Int]]].combine(aMap, anotherMap)

  println(combinedMap.get("foo"))
}
