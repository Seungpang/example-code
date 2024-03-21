package main.kotlin.lec15

fun main() {
    var array = arrayOf(100, 200)
    val plus = array.plus(300)
    println(plus)

    for (i in plus.indices) {
        println("${i} ${plus[i]}")
    }

    for ((idx, value) in array.withIndex()) {
        println("$idx $value")
    }

    val numbers = listOf(100, 200)
    val emptyList = emptyList<Int>()
    printNumbers(emptyList())

    val numbers2 = mutableListOf(100, 200)
    numbers2.add(300)

    val oldMap = mutableMapOf<Int, String>()
    oldMap[1] = "MONDAY"
    oldMap[2] = "MONDAY"

    mapOf(1 to "MONDAY", 2 to "TUESDAY")

    for (key in oldMap.keys) {
        println(key)
        println(oldMap[key])
    }

    for ((key, value) in oldMap.entries) {
        println(key)
        println(value)
    }
}

private fun printNumbers(numbers: List<Int>) {

}
