package me.seungpang.kotestpractice.adapter.out.persistence

import me.seungpang.kotestpractice.application.port.out.CreateTodoPort
import me.seungpang.kotestpractice.application.port.out.FindTodoPort
import me.seungpang.kotestpractice.application.port.out.UpdateTodoPort

class TodoPersistenceAdapter(
    val todoRepository: SpringDataTodoRepository,
    val todoMapper: TodoMapper,
) : CreateTodoPort, FindTodoPort, UpdateTodoPort {

}
