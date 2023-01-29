package task2

import org.scalatest.flatspec.AnyFlatSpec

class BuildingSpec extends AnyFlatSpec {
  "A Building" should "have countOldManFloors function" in {
    val building = ResidentsBuilding(
      "Innopolis",
      ResidentialFloor(
        ResidentialFloor(
          ResidentialFloor(
            ResidentialFloor(
              Attic(),
              (Girl(1), Girl(2))
            ),
            (Girl(3), Boy(4))
          ),
          (Boy(5), Girl(6))
        ),
        (Boy(7), Boy(8))
      )
    )
    val floors = Building.countOldManFloors(building, 4)
    val expectedFloors = 2
    assertResult(expectedFloors)(floors)
  }

  it should "have countOldManFloors function with 0 by default" in {
    val building = ResidentsBuilding(
      "Innopolis",
      ResidentialFloor(
        ResidentialFloor(
          ResidentialFloor(
            ResidentialFloor(
              Attic(),
              (Girl(1), Girl(2))
            ),
            (Girl(3), Boy(4))
          ),
          (Boy(5), Girl(6))
        ),
        (Boy(7), Boy(8))
      )
    )
    val floors = Building.countOldManFloors(building, 10)
    val expectedFloors = 0
    assertResult(expectedFloors)(floors)
  }

  it should "have countOldManFloors function working with girls only residents" in {
    val building = ResidentsBuilding(
      "Innopolis",
      ResidentialFloor(
        ResidentialFloor(
          ResidentialFloor(
            ResidentialFloor(
              Attic(),
              (Girl(1), Girl(2))
            ),
            (Girl(3), Girl(4))
          ),
          (Girl(5), Girl(6))
        ),
        (Girl(7), Girl(8))
      )
    )
    val floors = Building.countOldManFloors(building, 4)
    val expectedFloors = 0
    assertResult(expectedFloors)(floors)
  }

  it should "have countOldManFloors function working with Attic floor" in {
    val building = ResidentsBuilding("Innopolis", Attic())
    val floors = Building.countOldManFloors(building, 4)
    val expectedFloors = 0
    assertResult(expectedFloors)(floors)
  }

  it should "have womenMaxAge function" in {
    val building = ResidentsBuilding(
      "Innopolis",
      ResidentialFloor(
        ResidentialFloor(
          ResidentialFloor(
            ResidentialFloor(
              Attic(),
              (Girl(1), Girl(2))
            ),
            (Girl(6), Boy(4))
          ),
          (Boy(5), Girl(3))
        ),
        (Boy(7), Boy(8))
      )
    )
    val maxAge = Building.womenMaxAge(building)
    val expectedMaxAge = Some(6)
    assertResult(expectedMaxAge)(maxAge)
  }

  it should "have womenMaxAge function working with boys only residents" in {
    val building = ResidentsBuilding(
      "Innopolis",
      ResidentialFloor(
        ResidentialFloor(
          ResidentialFloor(
            ResidentialFloor(
              Attic(),
              (Boy(1), Boy(2))
            ),
            (Boy(6), Boy(4))
          ),
          (Boy(5), Boy(3))
        ),
        (Boy(7), Boy(8))
      )
    )
    val maxAge = Building.womenMaxAge(building)
    val expectedMaxAge = None
    assertResult(expectedMaxAge)(maxAge)
  }

  it should "have womenMaxAge function working with Attic floor" in {
    val building = ResidentsBuilding("Innopolis", Attic())
    val maxAge = Building.womenMaxAge(building)
    val expectedMaxAge = None
    assertResult(expectedMaxAge)(maxAge)
  }
}
