package me.seungpang.kotestpractice.application.port.`in`

import me.seungpang.kotestpractice.adapter.`in`.web.TodoResponse

interface CreateTodoUseCase {

    fun create(command: CreateTodoCommand): TodoResponse
}
