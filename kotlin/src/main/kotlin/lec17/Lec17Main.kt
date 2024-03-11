package main.kotlin.lec17

fun main() {
    val fruits = listOf(
        Fruit("사과", 1_000),
        Fruit("사과", 1_200),
        Fruit("사과", 1_200),
        Fruit("사과", 1_500),
        Fruit("바나나", 3_000),
        Fruit("바나나", 3_200),
        Fruit("바나나", 2_500),
        Fruit("바나나", 10_000),
    )

    val isApple = fun(fruit: Fruit): Boolean {
        return fruit.name == "사과"
    }

    val isApple2: (Fruit) -> Boolean = { fruits: Fruit -> fruits.name == "사과"}

    isApple(fruits[0])
    isApple.invoke(fruits[0])

    println(filterFruits(fruits, isApple))
    filterFruits(fruits) { it.name == "사과"}
}

private fun filterFruits(
    fruits: List<Fruit>, filter: (Fruit) -> Boolean
): List<Fruit> {
//    val results = mutableListOf<Fruit>()
//    for (fruit in fruits) {
//        if (filter(fruit)) {
//            results.add(fruit)
//        }
//    }
//    return results

    return fruits.filter(filter)
}
