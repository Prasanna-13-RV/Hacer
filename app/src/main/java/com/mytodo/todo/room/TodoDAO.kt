package com.mytodo.todo.room

import android.database.SQLException
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow
import java.sql.PreparedStatement
import java.time.LocalDate
import java.time.LocalDateTime

@Dao
interface TodoDAO {

    @Upsert
    suspend fun addMyTodo(todoEntity: TodoEntity)

    @Query("SELECT * FROM TodoEntity")
    fun getAllTodos() : Flow<List<TodoEntity>>

    @Query("SELECT * FROM TodoEntity WHERE todoId = :id")
    fun getTodoById(id : Int) : Flow<List<TodoEntity>>

    @Delete
    suspend fun deleteTodo(todoEntity: TodoEntity)

    @Query("DELETE FROM TodoEntity WHERE todoId = :id")
    suspend fun deleteById(id : Int)

    @Query("DELETE FROM TodoEntity WHERE todoDate <= :cutoffDate")
    fun deleteItemsOlderThan(cutoffDate: Long)



}