package main.kotlin.lec12

fun main() {
}

class Person private constructor(
    var name: String,
    var age: Int,
){

    companion object Factory : Log {
        private const val MIN_AGE = 1 // const-> 진짜 상수에 붙이기 위한 용도

        @JvmStatic
        fun newBaby(name: String): Person {
            return Person(name, MIN_AGE)
        }

        override fun log() {
            println("나는 Person 클래스 동행객체 Factory입니다.")
        }
    }
}
