package main.kotlin.lec14

fun main() {
    val person1 = PersonDto("김승래", 100)
    val person2 = PersonDto("김승래", 100)
    println(person1 == person2)
}

data class PersonDto(
    val name: String,
    val age: Int,
)
