package com.mytodo.todo.viewmodel

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mytodo.todo.repository.Repository
import com.mytodo.todo.room.TodoEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class TodoViewModel @Inject constructor(val repository: Repository) : ViewModel() {

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