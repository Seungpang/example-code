package main.kotlin

fun main() {
    val person2 = Person2("kim")
    println(person2.name)
    person2.name = "kim"
    println(person2.name)
}

class Person2(
    name: String = "kim",
    var age: Int = 1
) {

    var name = name
        set(value) {
            field = value.uppercase()
        }

//    fun getUppercaseName(): String {
//        return this.name.uppercase()
//    }
//
//    val uppercaseName: String
//        get() = this.name.uppercase()

    init {
        if (age <= 0) {
            throw IllegalArgumentException("나이는 ${age}일 수 없습니다.")
        }
        println("초기화 블록")
    }

    val isAdult: Boolean
        get() = this.age >= 20
}
