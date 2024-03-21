package main.kotlin.lec16

fun main() {
    println("ABC".lastChar())
}

fun String.lastChar(): Char {
    return this[this.length - 1]
}

