package main.kotlin.lec19

import main.kotlin.lec19.a.printHelloWorld as printHelloWorldA
import main.kotlin.lec19.b.printHelloWorld as printHelloWorldB

data class Person(
    val name: String,
    val age: Int
)

class Person2(
    val name: String,
    val age: Int
) {
    operator fun component1(): String {
        return this.name
    }

    operator fun component2(): Int {
        return this.age
    }
}


fun main() {
    printHelloWorldA()
    printHelloWorldB()

    val person = Person("김승래", 100)
    val (name, age) = person
    println("이름 : ${name}, 나이 : ${age}")

    val person2 = Person2("김승래", 100)
    val (name2, age2) = person2
    println("이름 : ${name2}, 나이 : ${age2}")


    val numbers = listOf(1, 2, 3)
    numbers.map { number -> number + 1}
        .forEach {number -> println(number) }

    run {
        numbers.forEach { number ->
            if (number == 2) {
                return@run
            }
        }
    }

    numbers.forEach {number ->
        if (number == 2) {
            return@forEach
        }
    }

    abc@ for (i in 1..100) {
        for (j in 1..100) {
            if (j == 2) {
                break@abc
            }
            println("${i} ${j}")
        }
    }
}
