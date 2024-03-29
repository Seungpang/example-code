package me.seungpang.kotestpractice.domain

import java.time.LocalDateTime

data class Todo(
    val id: Long?,
    val title: String,
    val description: String,
    val done: Boolean,
    val createAt: LocalDateTime,
    val updateAt: LocalDateTime?,
)
