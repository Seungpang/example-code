package main.kotlin

fun main() {

    try {
        throw Exception()
    } catch (e: Exception) {
        println("예외 발생")
    } finally {
        println("finally 실행")
    }

    val a = try {
        "1234".toInt()
    } catch (e: Exception) {
        println("예외 발생")
    }
    println(a)

    val b : String? = "vv"
    val c : String = b ?: failFast("예외 발생")

    println(c.length)
}

fun failFast(message: String): Nothing {
    throw IllegalArgumentException(message)
}
