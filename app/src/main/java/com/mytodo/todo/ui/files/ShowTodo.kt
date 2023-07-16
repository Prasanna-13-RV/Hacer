package com.mytodo.todo.ui.files

import android.content.Context
import android.content.Intent
import android.os.Build
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ComposableTarget
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import androidx.navigation.NavController
import androidx.room.TypeConverter
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.WorkRequest
import com.mytodo.todo.MyWork
import com.mytodo.todo.R
import com.mytodo.todo.data.NotificationService
import com.mytodo.todo.di.DeleteDi
import com.mytodo.todo.room.TodoEntity
import com.mytodo.todo.ui.data.todoList
import com.mytodo.todo.ui.theme.MyTodoTheme
import com.mytodo.todo.utils.DateConvertors
import com.mytodo.todo.viewmodel.TodoViewModel
import java.time.LocalDateTime


val convertors = DateConvertors()

@Composable
fun ShowTodo(navController: NavController?, viewModel: TodoViewModel?) {
    AppBarTopShow(navController!!)
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 80.dp)
    ) {
//    AppNameBar()

        val todos by viewModel!!.todos.collectAsState(initial = emptyList())


        AllCards(navController, todos)
        val context = LocalContext.current.applicationContext

        FloatingActionButton(
            onClick = {
                navController.navigate("addtodo")
//
//                Intent(context, NotificationService::class.java).also {
//
//                        it.action = NotificationService.Actions.STOP.toString()
//                        ContextCompat.startForegroundService(context, it)
//                    }
//
            },
            modifier = Modifier
                .padding(all = 16.dp)
                .align(alignment = Alignment.BottomEnd),

            ) {
            Icon(
                imageVector = Icons.Filled.Add,
                contentDescription = "Add Todo"
            )
        }
    }
}

@Composable
fun AllCards(navController: NavController, todos: List<TodoEntity>) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
    ) {
        item {
            if (todos.isEmpty()) {
                Text(
                    modifier = Modifier.padding(
                        horizontal = 10.dp
                    ),
                    text = "No todos found",
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp,
                )
            }
        }

        items(todos) { todo ->
            CardTodo(
                heading = todo.todoTitle,
                description = todo.todoDescription,
                date = todo.todoDate,
                navController,
                id = todo.todoId
            )
        }
    }
}


@Composable
fun CardTodo(
    heading: String,
    description: String,
    date: String?,
    navController: NavController,
    id: Int
) {
    val context = LocalContext.current
    Card(
        modifier = Modifier
            .fillMaxSize()
            .wrapContentHeight()
            .padding(horizontal = 15.dp, vertical = 6.dp)
            .clickable {
                navController.navigate("singletodo/$heading/$description/$id")
            }
    ) {
        Column(
            modifier = Modifier
                .padding(10.dp)
//                .clickable { WorkerFun(context) },
        ) {
            Text(
                text = heading,
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp,
            )
            Text(
                text = description,
                Modifier
                    .padding(start = 10.dp)
                    .clickable { convertors.dateDeletion(convertors.fromTimestamp(date!!)) },
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
                softWrap = true,
            )

            Row(
                modifier = Modifier.fillMaxWidth()
            ) {
                convertors.dateFormatter(convertors.fromTimestamp(date))?.let {
                    Text(
                        text = it,
                        Modifier.padding(start = 10.dp),
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis,
                        softWrap = true,
                    )
                }
                convertors.timeFormatter(convertors.fromTimestamp(date))?.let {
                    Text(
                        text = it,
                        Modifier.padding(start = 10.dp),
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis,
                        softWrap = true,
                    )
                }
            }

        }
    }
}

//private fun WorkerFun(context: Context) {
//    val mRequest: WorkRequest = OneTimeWorkRequestBuilder<DeleteDi>()
//        .build()
//
//    WorkManager.getInstance(context)
//        .enqueue(mRequest)
//}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppBarTopShow(navController: NavController) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(80.dp)
    ) {
        TopAppBar(
            modifier = Modifier.shadow(elevation = 8.dp),
            title = { Text(text = stringResource(id = R.string.app_name)) },
            navigationIcon = {
                IconButton(onClick = {
                    navController.popBackStack("ShowTodo", inclusive = true)
                }) {
                    Icon(
                        imageVector = Icons.Filled.Home,
                        contentDescription = "Go back"
                    )
                }
            },
        )
    }
}


@Preview(showBackground = true)
@Composable
fun ShowTodoPreview() {
    MyTodoTheme {
        ShowTodo(null, null)
    }
}

