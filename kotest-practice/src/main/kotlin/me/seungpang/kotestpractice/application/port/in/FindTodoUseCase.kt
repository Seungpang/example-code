package me.seungpang.kotestpractice.application.port.`in`

import me.seungpang.kotestpractice.adapter.`in`.web.TodoResponse

interface FindTodoUseCase {

    fun findAll(): List<TodoResponse>

    fun findById(id: Long): TodoResponse
}
