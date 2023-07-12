package com.mytodo.todo.repository

import com.mytodo.todo.room.TodoDB
import com.mytodo.todo.room.TodoEntity

class Repository(val todoDB: TodoDB) {

    suspend fun addTodoToRoom(todoEntiy : TodoEntity) {
        todoDB.todoDao().addMyTodo(todoEntiy)
    }

    fun getAllTodos() = todoDB.todoDao().getAllTodos()


    suspend fun deleteByIdTodoFromRoom(id: Int) {
        todoDB.todoDao().deleteById(id)
    }


    suspend fun deleteTodoFromRoom(todoEntiy: TodoEntity) {
        todoDB.todoDao().deleteTodo(todoEntiy)
    }


}