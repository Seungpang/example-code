package main.kotlin

import java.io.FileWriter

fun main() {
    FileWriter("test.txt")
        .use{
            it.write("Hello Kotlin")
        }
}
