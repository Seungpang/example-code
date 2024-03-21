package main.kotlin

fun main() {
    printAgeIfPerson(Person("", 10))
    val number1 = 3
    val number2: Long = number1.toLong()

    println(number1 + number2)

    val number3: Int? = 3
    val number4: Long = number3?.toLong() ?: 0L

    val person = Person("김승래", 100)
    println("이름 : ${person.name}")
    val name = "김승래"

    val str = """
        신기하네
        이열
        ${name}    
    """.trimIndent()
    println(str)

    val s = "ABC"
    println(s[0])
    println(s[2])
}

fun printAgeIfPerson(obj: Any?) {
    val person = obj as? Person
    println(person?.age)
    if (obj is Person) {
        println(obj.age) // => 스마트 캐스트
    }

    if (obj !is Person) {
    }
}
