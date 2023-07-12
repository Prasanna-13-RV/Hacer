package com.mytodo.todo.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mytodo.todo.repository.Repository
import com.mytodo.todo.room.TodoEntity
import kotlinx.coroutines.launch

class TodoViewModel(val repository: Repository) : ViewModel() {

    fun addMyTodo(todo : TodoEntity) {
        viewModelScope.launch {
            repository.addTodoToRoom(todo)
        }
    }

    val todos = repository.getAllTodos()


    fun deleteTodo(todo : TodoEntity) {
        viewModelScope.launch {
            repository.deleteTodoFromRoom(todo)
        }
    }

    fun deleteByIdTodo(id : Int) {
        viewModelScope.launch {
            repository.deleteByIdTodoFromRoom(id)
        }
    }


}