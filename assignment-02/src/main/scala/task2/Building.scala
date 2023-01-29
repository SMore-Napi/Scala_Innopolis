package task2

// Building should have:
//   - string address
//   - floors (link to first floor) Floor can be either residential floor (жилое помещение) or attic (чердак)
//     Each residential floor has two persons living on it and ladder to next floor (just a link)
//     Attic has no person living in it and no people above it
//     Remember the stuff you have learnt with List exercises, they will become handy
//   - Each person has age and sex (male/female) (What kind of class from the lab can be used to store people?)

abstract class Building
case class ResidentsBuilding(address: String, firstFloor: Floor) extends Building
abstract class Floor
case class Attic() extends Floor
case class ResidentialFloor(nextFloor: Floor, people: (Person, Person)) extends Floor

abstract class Person(age: Int, sex: Sex)

case class Boy(age: Int) extends Person(age, Male())

case class Girl(age: Int) extends Person(age, Female())
abstract class Sex

case class Male() extends Sex
case class Female() extends Sex

object Building {

  // Traverse building bottom to top applying function [f] on each residential floor with the initial accumulator
  //  [acc0]
  def protoFold[T](building: Building, acc0: T)(f: (T, ResidentialFloor) => T): T = building match {
    case ResidentsBuilding(_, _: Attic) => acc0
    case ResidentsBuilding(a, floor: ResidentialFloor) =>
      protoFold(ResidentsBuilding(a, floor.nextFloor), f(acc0, floor))(f)
  }

  // Count number of floors where there is at least one man older than [olderThan] NOTE: use [protoFold]
  def countOldManFloors(building: Building, olderThan: Int): Int =
    protoFold(building, 0)((acc: Int, floor: ResidentialFloor) =>
      floor.people match {
        case (Boy(age), _) if age > olderThan => acc + 1
        case (_, Boy(age)) if age > olderThan => acc + 1
        case _                                => acc
      }
    )

  // Find age of the oldest woman NOTE: use [protoFold]
  def womenMaxAge(building: Building): Option[Int] =
    protoFold(building, None: Option[Int])((maxAge: Option[Int], floor: ResidentialFloor) => {
      val getMaxAge: (Option[Int], Int) => Option[Int] = (maxAge, age) =>
        maxAge match {
          case None    => Some(age)
          case Some(m) => if (age >= m) Some(age) else Some(m)
        }
      floor.people match {
        case (Boy(_), Boy(_))         => maxAge
        case (Girl(age), Boy(_))      => getMaxAge(maxAge, age)
        case (Boy(_), Girl(age))      => getMaxAge(maxAge, age)
        case (Girl(age1), Girl(age2)) => getMaxAge(maxAge, if (age1 >= age2) age1 else age2)
      }
    })
}
