package com.mytodo.todo.room

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class TodoEntity(
    @PrimaryKey(autoGenerate = true)
    val todoId: Int,
    val todoTitle: String,
    val todoDescription: String,

)
