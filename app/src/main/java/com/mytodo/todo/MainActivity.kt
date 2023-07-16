package com.mytodo.todo

import android.Manifest
import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.OneTimeWorkRequest
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.mytodo.todo.data.NotificationService
import com.mytodo.todo.di.DeleteDi
import com.mytodo.todo.repository.Repository
import com.mytodo.todo.room.TodoDB
import com.mytodo.todo.ui.files.AddTodo
import com.mytodo.todo.ui.files.ShowTodo
import com.mytodo.todo.ui.files.SingleTodo
import com.mytodo.todo.ui.theme.MyTodoTheme
import com.mytodo.todo.viewmodel.TodoViewModel
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.lifecycle.HiltViewModel
import java.util.concurrent.TimeUnit
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU){
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.POST_NOTIFICATIONS),
                0
            )
        }

        setContent {
            MyTodoTheme {
                // A surface container using the 'background' color from the theme
                val navController = rememberNavController()
//                val mContext = LocalContext.current
//                val db = TodoDB.getInstance(mContext)
//                val repository = Repository(db)
//                val myViewModel = TodoViewModel(repository = repository)

                val workRequest = OneTimeWorkRequest.Builder(DeleteDi::class.java).build()
                WorkManager.getInstance(this.applicationContext).enqueue(workRequest)

                val deleteRequest = PeriodicWorkRequestBuilder<DeleteDi>(
                    repeatInterval = 2,
                    repeatIntervalTimeUnit = TimeUnit.MINUTES
                ).build()

                val workManager = WorkManager.getInstance(this.applicationContext)
                workManager.enqueueUniquePeriodicWork(
                    "LogCleanup",
                    ExistingPeriodicWorkPolicy.CANCEL_AND_REENQUEUE,
                    deleteRequest
                    )

                val myViewModel = hiltViewModel<TodoViewModel>()

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