package main.kotlin

data class Person(val name: String, val age: Int) {
}


fun main() {
    val person1 = Person(name = "seungpang", age = 20)

    println("이름=${person1.component1()}, 나이=${person1.component2()}")

    val (name, age) = person1

    println("이름=${name}, 나이=${age}")

    val person2 = person1.copy(name = "pang")
    println(person2)
//    println(person1.toString())
//    println(person1 == person2)

//    val set = hashSetOf(person1)
//    println(set.contains(person2))

//    person1.name = "strange"
//    println(set.contains(person1))
}


