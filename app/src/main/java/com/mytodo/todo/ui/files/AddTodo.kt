package com.mytodo.todo.ui.files

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.mytodo.todo.R
import com.mytodo.todo.room.TodoEntity
import com.mytodo.todo.ui.theme.InnerTextFieldLight
import com.mytodo.todo.ui.theme.MyTodoTheme
import com.mytodo.todo.viewmodel.TodoViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddTodo(
    navController: NavController?,
    viewModel: TodoViewModel?
) {
    var title by remember {
        mutableStateOf("")
    }
    var description by remember {
        mutableStateOf("")
    }

    var alertBool by remember {
        mutableStateOf(false)
    }

    Column() {
        AppBarTopAdd(navController!!)
        Box(modifier = Modifier.fillMaxSize()) {

            Column(
                modifier = Modifier
                    .padding(horizontal = 20.dp, vertical = 10.dp)
                    .fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
//                verticalArrangement = Arrangement.Center
            ) {

                Column(
                    modifier = Modifier
                        .weight(1f)
                ) {


                    Text(
                        text = stringResource(id = R.string.todo_title),
                        modifier = Modifier
                            .align(alignment = Alignment.Start),
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 19.sp,
//                    color = Color.DarkGray
                    )
                    TextField(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 5.dp),
                        shape = RoundedCornerShape(8.dp),
                        value = title,
                        onValueChange = { ti -> title = ti },
                        colors = TextFieldDefaults.textFieldColors(
                            disabledIndicatorColor = Color.Transparent,
                            focusedIndicatorColor = Color.Transparent,
                            errorIndicatorColor = Color.Transparent,
                            unfocusedIndicatorColor = Color.Transparent
                        ),
//                label = {
//                    Text(text = "Todo title")
//                },
                        placeholder = {
                            Text(text = stringResource(id = R.string.todo_titlepla))
                        }
                    )

                    Text(
                        text = stringResource(id = R.string.todo_description),
                        modifier = Modifier
                            .align(alignment = Alignment.Start)
                            .padding(vertical = 5.dp),

                        fontWeight = FontWeight.SemiBold,
                        fontSize = 19.sp,
//                    color = Color.DarkGray
                    )

                    TextField(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 5.dp)
                            .defaultMinSize(200.dp)
                        ,
                        value = description,
                        shape = RoundedCornerShape(8.dp),
                        onValueChange = { pres -> description = pres },
                        colors = TextFieldDefaults.textFieldColors(
                            disabledIndicatorColor = Color.Transparent,
                            focusedIndicatorColor = Color.Transparent,
                            errorIndicatorColor = Color.Transparent,
                            unfocusedIndicatorColor = Color.Transparent
                        ),

//                label = {
//                    Text(text = "Todo description")
//                },
                        singleLine = false,
                        placeholder = {
                            Text(text = stringResource(id = R.string.todo_descriptionpla))
                        }
                    )
                }

                val context = LocalContext.current

                Button(modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 5.dp),
                    onClick = {
                        alertBool = true
                    }) {
                    if (alertBool) {
                        AlertFunction(
                            viewModel!!,
                            title,
                            description,
                            navController!!,
                            { alertBool = false })
                    }

                    Text(text = stringResource(id = R.string.addButton))
                }
            }
        }
    }
}

@Composable
fun AlertFunction(
    viewModel: TodoViewModel,
    title: String,
    description: String,
    navController: NavController,
    alertBool: () -> Unit
) {
    AlertDialog(onDismissRequest = { /*TODO*/ },
        text = { Text("Confirm to add the todo") },
        confirmButton = {
            TextButton(onClick = {
                viewModel.addMyTodo(TodoEntity(0, title, description))
                navController.navigate("showtodo")
            }) {
                Text(text = stringResource(id = R.string.confirm))
            }
        },
        dismissButton = {
            TextButton(onClick = alertBool) {
                Text(text = stringResource(id = R.string.cancel))
            }
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppBarTopAdd(navController: NavController) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(80.dp)
    ) {
        TopAppBar(
            modifier = Modifier.shadow(elevation = 8.dp),
            title = { Text(text = stringResource(id = R.string.topAddBar)) },
            navigationIcon = {
                IconButton(onClick = {
                    navController.popBackStack("addtodo", inclusive = true)
                }) {
                    Icon(
                        imageVector = Icons.Filled.ArrowBack,
                        contentDescription = "Go back"
                    )
                }
            },
        )
    }
}


@Preview(showBackground = true)
@Composable
fun AddPreview() {
    MyTodoTheme {
        val navController = rememberNavController()
        AddTodo(navController, null)
    }
}

