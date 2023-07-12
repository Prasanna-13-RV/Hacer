package com.mytodo.todo.ui.data

import androidx.annotation.DrawableRes
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class TodoData(
    val todoTitle: String,
    val todoDescription: String,
    @PrimaryKey(autoGenerate = true)
    val todoId: Int = 0
//    @DrawableRes val todoImage : Int
)

var todo1 = TodoData("Todo1", "Todo1Description")
var todo2 = TodoData("Todo2", "Todo1Description")
var todo3 = TodoData("Todo3", "Todo1Description")
var todo4 = TodoData("Todo4", "Todo1Description")


val todoList = listOf<TodoData>(todo1, todo2, todo3, todo4)
