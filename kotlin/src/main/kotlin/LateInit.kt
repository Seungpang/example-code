class LateExample {

    lateinit var text: String

    val textInitialized: Boolean
        get() = this::text.isInitialized

    fun printText() {
        println(text)
    }
}

fun main() {
    val test = LateExample()

    if (!test.textInitialized) {
        test.text = "하이"
    }
    test.printText()
}
