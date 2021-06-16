package com.example.jetpacksample.ui.feature.add.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.jetpacksample.R
import com.example.jetpacksample.ui.data.TodoDatabase
import com.example.jetpacksample.ui.data.TodoRepository
import com.example.jetpacksample.ui.feature.add.model.AddTodoViewState
import com.example.jetpacksample.ui.model.Todo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.joda.time.LocalDate

class AddTodoViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: TodoRepository
    private val viewStateData: MutableLiveData<AddTodoViewState> = MutableLiveData()
    val viewState: LiveData<AddTodoViewState> = viewStateData

    init {
        val dao = TodoDatabase.getDatabase(application).todoDao()
        repository = TodoRepository(dao)
    }

    fun save(title: String?, description: String?, dueDate: LocalDate?) {
        if (verifyInformation(title, description, dueDate)) {
            val todo = Todo(title = title!!, description = description!!, dueDate = dueDate!!)
            insert(todo)
            viewStateData.postValue(AddTodoViewState(didSave = true))
        }
    }

    private fun verifyInformation(title: String?, description: String?, dueDate: LocalDate?): Boolean {
        var isVerified = true
        if (title.isNullOrEmpty()) {
            viewStateData.value =
                AddTodoViewState(R.string.error_title_missing, AddTodoViewState.TITLE, false)
            isVerified = false
        }
        if (description.isNullOrEmpty()) {
            viewStateData.value = AddTodoViewState(R.string.error_description_missing, AddTodoViewState.DESCRIPTION, false)
            isVerified = false
        }

        val localDate = LocalDate.now()
        if (dueDate != null && dueDate.isBefore(localDate)) {
            viewStateData.value =
                AddTodoViewState(R.string.error_due_date_invalid, AddTodoViewState.DUE_DATE, false)
            isVerified = false
        } else if (dueDate == null) {
            viewStateData.value =
                AddTodoViewState(R.string.error_due_date_missing, AddTodoViewState.DUE_DATE, false)
            isVerified = false
        }
        return isVerified
    }

    private fun insert(todo: Todo) = viewModelScope.launch(Dispatchers.IO) {
        repository.insert(todo)
    }
}