//package main.kotlin
//
//sealed class Developer {
//
//    abstract val name: String
//    abstract fun code(language: String)
//}
//
//data class BackendDeveloper(override val name: String) : Developer() {
//
//    override fun code(language: String) {
//        println("저는 백엔드 개발자입니다. ${language}를 사용합니다.")
//    }
//}
//
//data class FrontendDeveloper(override val name: String) : Developer() {
//
//    override fun code(language: String) {
//        println("저눈 프론트엔드 개발자입니다. ${language}를 사용합니다.")
//    }
//}
//
//object OtherDeveloper : Developer() {
//
//    override val name: String = "익명"
//
//    override fun code(language: String) {
//        TODO("Not yet implemented")
//    }
//}
//
//data class AndroidDeveloper(override val name: String) : Developer() {
//
//    override fun code(language: String) {
//        println("저눈 안드로이드 개발자입니다. ${language}를 사용합니다.")
//    }
//}
//
//object DeveloperPool {
//    val pool = mutableMapOf<String, Developer>()
//
//    fun add(developer: Developer) = when(developer) { //컴파일러 입장에서는 Developer에 대한 타입이 몇개인지 알 수 없다. 하지만 sealed class일 경우에는 알 수 있다.
//        is BackendDeveloper -> pool[developer.name] = developer
//        is FrontendDeveloper -> pool[developer.name] = developer
//        is AndroidDeveloper -> pool[developer.name] = developer
//        is OtherDeveloper -> println("지원하지않는 개발자 종류입니다.")
//    }
//
//    fun get(name: String) = pool[name]
//}
//
//fun main() {
//    val backendDeveloper = BackendDeveloper(name="승팡")
//    DeveloperPool.add(backendDeveloper)
//
//    val frontendDeveloper = FrontendDeveloper(name="승래")
//    DeveloperPool.add(frontendDeveloper)
//
//    val androidDeveloper = AndroidDeveloper(name = "갤럭시")
//    DeveloperPool.add(androidDeveloper)
//
//    println(DeveloperPool.get("승팡"))
//    println(DeveloperPool.get("승래"))
//    println(DeveloperPool.get("갤럭시"))
//}
