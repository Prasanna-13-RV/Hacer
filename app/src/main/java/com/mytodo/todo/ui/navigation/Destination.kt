package com.mytodo.todo.ui.navigation

sealed class Destination(val route : String) {
    object ShowTodo : Destination("Show Todo")
    object AddTodo : Destination("Add Todo")
}
