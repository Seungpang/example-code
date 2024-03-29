package me.seungpang.kotestpractice.adapter.out.persistence

import org.springframework.data.jpa.repository.JpaRepository

interface SpringDataTodoRepository : JpaRepository<TodoJpaEntity, Long> {

    fun findAllByDoneIsFalseOrderByIdDesc(): List<TodoJpaEntity>?
}
