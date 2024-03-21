package main.kotlin.lec10

class Cat(
    species: String
) : Animal(species, 4) {

    override fun move() {
        println("고양기가 사뿐 사뿐 걸어가")
    }

}
