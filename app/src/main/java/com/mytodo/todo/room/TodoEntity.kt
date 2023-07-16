package com.mytodo.todo.room

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.mytodo.todo.utils.DateConvertors
import java.time.LocalDateTime


val convertors = DateConvertors()
@Entity
data class TodoEntity (
    @PrimaryKey(autoGenerate = true)
    val todoId: Int,
    val todoTitle: String,
    val todoDescription: String,
    val todoDate: String? = convertors.dateToTimestamp(LocalDateTime.now())
)
