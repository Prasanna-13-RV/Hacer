package com.mytodo.todo.ui.files

import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
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
import androidx.navigation.NavController
import com.mytodo.todo.R
import com.mytodo.todo.room.TodoEntity
import com.mytodo.todo.ui.data.todoList
import com.mytodo.todo.ui.theme.MyTodoTheme
import com.mytodo.todo.viewmodel.TodoViewModel


@Composable
fun ShowTodo(navController: NavController?, viewModel : TodoViewModel?) {
    AppBarTopShow(navController!!)
    Box(modifier = Modifier
        .fillMaxSize()
        .padding(top = 80.dp)) {
//    AppNameBar()

        val todos by viewModel!!.todos.collectAsState(initial = emptyList())


        AllCards(navController, todos)
        val context = LocalContext.current.applicationContext


        FloatingActionButton(
            onClick = {
                navController.navigate("addtodo")
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
fun AllCards(navController: NavController, todos : List<TodoEntity>) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
    ) {
        items(todos) { todo ->
            CardTodo(heading = todo.todoTitle, description = todo.todoDescription, navController, id = todo.todoId)
        }
    }
}

@Composable
fun CardTodo(heading: String, description: String, navController : NavController, id : Int) {
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

        ) {
            Text(
                text = heading,
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp,
            )
            Text(
                text = description,
                Modifier.padding(start = 10.dp),
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
                softWrap = true,
            )
        }
    }
}


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

