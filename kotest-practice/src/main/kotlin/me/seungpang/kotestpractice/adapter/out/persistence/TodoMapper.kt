package me.seungpang.kotestpractice.adapter.out.persistence

import me.seungpang.kotestpractice.domain.Todo
import org.springframework.stereotype.Component

@Component
class TodoMapper {

    fun mapToDomainEntity(todo: TodoJpaEntity): Todo {
        return Todo(todo.id, todo.title, todo.description, todo.done, todo.createAt, todo.updatedAt)
    }
}
