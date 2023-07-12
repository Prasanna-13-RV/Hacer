package com.mytodo.todo.ui.files

import android.content.Intent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.mytodo.todo.repository.Repository
import com.mytodo.todo.room.TodoDB
import com.mytodo.todo.room.TodoEntity
import com.mytodo.todo.ui.theme.MyTodoTheme
import com.mytodo.todo.viewmodel.TodoViewModel

@Composable
fun SingleTodo(
    todoId: String,
    todoHeading: String,
    todoDescription: String,
    navController: NavController,
    viewModel: TodoViewModel?
) {
    Column {
        AppBarTopSingle(navController, todoId, todoHeading, todoDescription)
        Box(
            modifier = Modifier
                .padding(horizontal = 15.dp, vertical = 10.dp)
                .weight(1f)
        ) {
            LazyColumn {
                item {
                    Text(
                        text = todoHeading.replaceFirstChar(Char::titlecase),
                        fontWeight = FontWeight.Bold,
                        fontSize = 30.sp
                    )
                    Text(
                        text = todoDescription, fontSize = 20.sp
                    )
                }
            }
        }
        BottomShare(todoId, viewModel!!, navController, todoHeading, todoDescription)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppBarTopSingle(
    navController: NavController, todoId: String, todoHeading: String, todoDescription: String
) {
    Box(
        modifier = Modifier.fillMaxWidth()
//            .height(80.dp)
    ) {
        TopAppBar(
            modifier = Modifier.shadow(elevation = 8.dp),
            title = { Text(text = todoHeading) },
            navigationIcon = {
                IconButton(onClick = {
                    navController.popBackStack(
                        "singletodo/$todoHeading/$todoDescription/$todoId", inclusive = true
                    )
                }) {
                    Icon(
                        imageVector = Icons.Filled.ArrowBack, contentDescription = "Go back"
                    )
                }
            },

            )
    }
}

@Composable
fun BottomShare(
    todoId: String,
    viewModel: TodoViewModel,
    navController: NavController,
    todoHeading: String,
    todoDescription: String
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(50.dp)
            .padding(horizontal = 15.dp)
            .padding(end = 10.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceAround
        ) {
            var isUpdateButton by remember {
                mutableStateOf(false)
            }
            var isDeleteButton by remember {
                mutableStateOf(false)
            }
            val context = LocalContext.current
            IconButton(
                onClick = {
                    val i = Intent(Intent.ACTION_SEND)

                    // on below line we are passing email address,
                    // email subject and email body
                    i.putExtra(Intent.EXTRA_SUBJECT, todoHeading)
                    i.putExtra(Intent.EXTRA_TEXT, todoDescription)

                    // on below line we are
                    // setting type of intent
//                    i.type = "message/rfc822"
                    i.type = "text/plain"

                    // on the below line we are starting our activity to open email application.
                    context.startActivity(Intent.createChooser(i, "Choose a client : "))

                }
            ) {
                Icon(
                    imageVector = Icons.Filled.Share, contentDescription = "Share Button"
                )
            }
            if (isDeleteButton) {
                DeleteAlertBox(navController, viewModel, todoId) { isDeleteButton = false }
            }
            IconButton(onClick = {
                isDeleteButton = true
            }) {
                Icon(
                    imageVector = Icons.Filled.Delete, contentDescription = "Share Button"
                )
            }
            if (isUpdateButton) {
                AlertBox(
                    { isUpdateButton = false },
                    todoId,
                    todoHeading,
                    todoDescription,
                    viewModel,
                    navController
                )
            }
            IconButton(onClick = {
                isUpdateButton = true
            }) {
                Icon(
                    imageVector = Icons.Filled.Edit, contentDescription = "Share Button"
                )
            }

        }
    }
}

@Composable
fun DeleteAlertBox(
    navController: NavController,
    viewModel: TodoViewModel,
    todoId: String,
    isDeleteButton: () -> Unit
) {
    AlertDialog(
        onDismissRequest = { /*TODO*/ },
        text = { Text(text = "Are you sure you want to delete?") },
        confirmButton = {
            TextButton(onClick = {
                viewModel.deleteByIdTodo(todoId.toInt())
                navController.navigate("showtodo")
            }) {
                Text(text = "Yes")
            }
        },
        dismissButton = {
            TextButton(onClick = {
                isDeleteButton()
            }) {
                Text(text = "Cancel")
            }
        })
}

@Composable
fun AlertBox(
    isUpdateButton: () -> Unit,
    todoId: String,
    todoHeading: String,
    todoDescription: String,
    viewModel: TodoViewModel,
    navController: NavController,
) {
    AlertDialog(onDismissRequest = { /*TODO*/ }, text = {
        UpdateTodos(
            isUpdateButton, todoId, viewModel, navController, todoHeading, todoDescription
        )
    }, confirmButton = {},
        dismissButton = {
            TextButton(onClick = {
                isUpdateButton()
            }) {
                Text(text = "Cancel")
            }
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UpdateTodos(
    isUpdateButton: () -> Unit,
    todoId: String,
    viewModel: TodoViewModel,
    navController: NavController,
    todoHeading: String,
    todoDescription: String
) {
    Column {

        var title by remember {
            mutableStateOf(todoHeading)
        }
        var description by remember {
            mutableStateOf(todoDescription)
        }

        Box(modifier = Modifier) {
            Column(
                modifier = Modifier
                    .padding(top = 20.dp)
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "Todo Title",
                    modifier = Modifier.align(alignment = Alignment.Start),
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 19.sp,
                )
                TextField(modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 5.dp),
                    shape = RoundedCornerShape(8.dp),
                    value = title,
                    colors = TextFieldDefaults.textFieldColors(
                        disabledIndicatorColor = Color.Transparent,
                        focusedIndicatorColor = Color.Transparent,
                        errorIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent
                    ),
                    onValueChange = { ti -> title = ti },
                    placeholder = {
                        Text(text = "Task")
                    })
                Text(
                    text = "Todo description",
                    modifier = Modifier
                        .align(alignment = Alignment.Start)
                        .padding(vertical = 5.dp),

                    fontWeight = FontWeight.SemiBold,
                    fontSize = 19.sp,
//                    color = Color.DarkGray
                )
                TextField(modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 5.dp)
                    .height(150.dp),
                    value = description,
                    shape = RoundedCornerShape(8.dp),
                    onValueChange = { pres -> description = pres },
                    singleLine = false,
                    colors = TextFieldDefaults.textFieldColors(
                        disabledIndicatorColor = Color.Transparent,
                        focusedIndicatorColor = Color.Transparent,
                        errorIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent
                    ),
                    placeholder = {
                        Text(text = "Task Description")
                    })
                Button(modifier = Modifier.fillMaxWidth(), onClick = {
                    isUpdateButton()
                    viewModel.addMyTodo(TodoEntity(todoId.toInt(), title, description))
                    navController.navigate("showtodo")
                }) {
                    Text(text = "Update To-do")
                }
            }

        }
    }
}

@Preview(showBackground = true)
@Composable
fun SinglePagePreview() {
    val navController = rememberNavController()
    val todoHeading = "Hello World"
    val todoInt = "123"
    val todoDescription = "Hello World"
    MyTodoTheme {
        SingleTodo(
            todoInt,
            todoHeading,
            todoDescription,
            navController,
            null
        )
    }
}
