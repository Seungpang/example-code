package main.kotlin

fun main() {

    val client : DatabaseClient = DatabaseClient().apply {
        url = "localhost:3306"
        username = "mysql"
        password = "1234"
    }

    val connected = client.connect()
    println(connected)

    client.connect().run { println(this) }
}
