package com.mytodo.todo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.mytodo.todo.repository.Repository
import com.mytodo.todo.room.TodoDB
import com.mytodo.todo.ui.files.AddTodo
import com.mytodo.todo.ui.files.ShowTodo
import com.mytodo.todo.ui.files.SingleTodo
import com.mytodo.todo.ui.theme.MyTodoTheme
import com.mytodo.todo.viewmodel.TodoViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyTodoTheme {
                // A surface container using the 'background' color from the theme
                val navController = rememberNavController()
                val mContext = LocalContext.current
                val db = TodoDB.getInstance(mContext)
                val repository = Repository(db)
                val myViewModel = TodoViewModel(repository = repository)
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    NavHost(navController = navController, startDestination = "showtodo") {
                        composable(route = "showtodo") {
                            ShowTodo(navController, myViewModel)
                        }
                        composable(route = "addtodo") {
                            AddTodo(
                                navController,
                                myViewModel
                            )
                        }
                        composable(route = "singletodo/{heading}/{description}/{id}") {
                            val todoHeading = it.arguments?.getString("heading")
                            val todoDescription = it.arguments?.getString("description")
                            val todoId = it.arguments?.getString("id")
                            print("$todoId printme")

                            if (todoHeading != null && todoDescription != null && todoId != null) {
                                SingleTodo(todoId, todoHeading, todoDescription, navController, myViewModel)
                            }
                        }
                    }
                }
            }
        }
    }
}

