package com.mytodo.todo.repository

import android.os.Build
import androidx.annotation.RequiresApi
import com.mytodo.todo.room.TodoDB
import com.mytodo.todo.room.TodoEntity
import java.time.LocalDateTime
import javax.inject.Inject

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

//    fun deleteItemsOlderThan(days: Long) {
//        val cutoffDate = LocalDateTime.now().minusDays(days.toLocalDate().toEpochDay())
//        val cutoffDate = LocalDateTime.now().minusDays(days)
//        todoDB.todoDao().deleteItemsOlderThan(cutoffDate)
//    }
    fun deleteItemsOlderThan(days: Int) {
        val cutoffDate = System.currentTimeMillis() - (days * 24 * 60 * 60 * 1000)
        todoDB.todoDao().deleteItemsOlderThan(cutoffDate)
    }
}