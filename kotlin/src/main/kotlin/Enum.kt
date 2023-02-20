enum class PaymentStatus(val label: String) : Payable {
    UNPAID("미지금") {
        override fun isPayable(): Boolean  = true
    },

    PAID("지급완료") {
        override fun isPayable(): Boolean = false
    },
    FAILED("지급실패") {
        override fun isPayable(): Boolean = false
    },
    REFUNDED("환불") {
        override fun isPayable(): Boolean = false
    };
}

interface Payable {
    fun isPayable(): Boolean
}

fun main() {

//    if (PaymentStatus.UNPAID.isPayable()) {
//        println("결제 가능 상태")
//    }
//
//    val paymentStatus = PaymentStatus.valueOf("PAID")
//    println(paymentStatus.label)
//
//    if (paymentStatus == PaymentStatus.PAID) {
//        println("결제 완료 상태")
//    }

    for (status in PaymentStatus.values()) {
        println("[${status.name}](${status.label}) : ${status.ordinal}")
    }
}
