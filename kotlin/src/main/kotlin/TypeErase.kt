package main.kotlin

fun main() {
    val numbers = listOf(1, 2f, 3.0)
    numbers.filterIsInstance<Float>()
}

//fun <T> T.toSuperString() {
//    println("$T::class.java.name}: $this")
//}

inline fun <reified T> List<*>.hasAnyInstanceOf(): Boolean {
    return this.any { it is T }
}

class TypeErase {

}
