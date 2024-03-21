package main.kotlin

var x = 5
fun main() {
    x += 1
    println(x)

    val a: Int = 1

    val b = 1 //타입을 추론

    //지연 할당
    val c : Int
    c = 3

    val d : Int
    d = 123 //지연 할당할 때 타입을 명시하지 않으면 컴파일 오류 발생

    //val(value) java에 final 키워드 처럼 한번 할당한 후 재할당 불가
    //var(variable) 가변 변수
    var e : String = "Hello"
    e = "World"

    var f = 123
    //f = "hi" //한번 타입이 고정되면 타입 변경은 불가능
}
