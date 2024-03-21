package main.kotlin.lec20

import main.kotlin.Person

fun main() {

    mutableListOf("one", "two", "three")
        .also { println("The list elements before adding new one: $it") }
        .add("four")

    val numbers = mutableListOf("one", "two", "three")
    println("The list elements before adding new one: $numbers")
    numbers.add("four")
}

fun printPerson(person: Person?) {
    person?.let {
        println(it.name)
        println(it.age)
    }
}


fun solution(s: String, n: Int): String {
    return s.map {
        when {
            it.isLowerCase() -> 'a' + (it + n - 'a') % 26
            it.isUpperCase() -> 'A' + (it + n - 'A') % 26
            else -> ' '
        }
    }.joinToString("")
}
