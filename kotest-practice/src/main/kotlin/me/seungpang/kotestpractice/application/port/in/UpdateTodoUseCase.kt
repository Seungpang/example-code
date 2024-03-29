package me.seungpang.kotestpractice.application.port.`in`

import me.seungpang.kotestpractice.adapter.`in`.web.TodoResponse

interface UpdateTodoUseCase {

    fun update(id: Long, command: UpdateTodoCommand): TodoResponse
}
