package com.example.jetpacksample

import android.app.Application
import android.content.Intent
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.MaterialTheme.typography
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.jetpacksample.ui.MainViewModel
import com.example.jetpacksample.ui.feature.SettingsActivity
import com.example.jetpacksample.ui.feature.add.AddTodoActivity
import com.example.jetpacksample.ui.model.Todo

class MainActivity : AppCompatActivity() {
    private lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel =
            ViewModelProvider(this, MainViewModelFactory(application))[MainViewModel::class.java]
        setContent {
            Content(viewModel)
        }

    }

    class MainViewModelFactory(private val application: Application) : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return MainViewModel(application) as T
        }
    }
}

@Composable
fun Content(viewModel: MainViewModel) {
    val todos = viewModel.todoItems.observeAsState(initial = listOf())
    Scaffold(modifier = Modifier
        .fillMaxWidth()
        .fillMaxHeight(),
        topBar = {
            Menu()
        },
        floatingActionButton = {
            Fab()
        }, floatingActionButtonPosition = FabPosition.End,
        isFloatingActionButtonDocked = true,

        content = {
            TodoView(todos = todos.value)
        })
}

@Composable
fun TodoView(todos: List<Todo>) {
    if (todos.isEmpty()) {
        ShowEmptyTodoView()
    } else {
        ShowTodoView(todos = todos)
    }
}

@Composable
fun ShowEmptyTodoView() {
    Column(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth()
    ) {
        Text(text = "Add some work to do!", style = typography.h5)
    }
}

@Composable
fun ShowTodoView(todos: List<Todo>) {
    LazyColumn(modifier = Modifier.fillMaxWidth()) {
        items(todos.count(), itemContent = { index ->
            TodoListView(index = index, todos)
        })

    }
}

@Composable
fun TodoListView(index: Int, todos: List<Todo>) {
    Card(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth()
            .wrapContentHeight(),
        elevation = 2.dp,
        backgroundColor = colorResource(id = R.color.cardBackground),
        shape = RoundedCornerShape(corner = CornerSize(16.dp))
    ) {
        Column(modifier = Modifier.fillMaxWidth().padding(16.dp)) {
            Text(text = todos[index].title, style = typography.h6)
            Text(text = todos[index].description, style = typography.caption)
            Text(text = stringResource(id = R.string.due_date_adapter, todos[index].dueDate))
        }
    }
}

@Composable
fun Fab() {
    val context = LocalContext.current

    FloatingActionButton(onClick = {
        val intent = Intent(context, AddTodoActivity::class.java)
        context.startActivity(intent)
    }) {
        Icon(painter = painterResource(id = R.drawable.ic_add), contentDescription = "Add To Do")
    }
}

@Composable
fun Menu() {
    val context = LocalContext.current
    TopAppBar(title = {
        Text(
            text = stringResource(id = R.string.app_name),
            color = colorResource(id = R.color.colorToolbarTitle)
        )
    }, actions = {
        Image(
            painter = painterResource(id = R.drawable.ic_settings),
            contentDescription = stringResource(id = R.string.settings),
            modifier = Modifier
                .padding(paddingValues = PaddingValues(end = 4.dp))
                .clickable(onClick = {
                    val intent = Intent(context, SettingsActivity::class.java)
                    context.startActivity(intent)
                })
        )
    }, backgroundColor = colorResource(id = R.color.colorPrimary))
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    Scaffold(modifier = Modifier
        .fillMaxWidth()
        .fillMaxHeight(),
        topBar = {
            Menu()
        },
        floatingActionButton = {
            Fab()
        }, floatingActionButtonPosition = FabPosition.End,
        isFloatingActionButtonDocked = true,

        content = {
            TodoView(todos = com.example.jetpacksample.ui.preview.Preview.data)
        })
}