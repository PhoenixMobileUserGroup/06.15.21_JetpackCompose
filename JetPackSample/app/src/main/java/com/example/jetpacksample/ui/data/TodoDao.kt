package com.example.jetpacksample.ui.data

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.jetpacksample.ui.model.Todo

@Dao
interface TodoDao {

    @Query("SELECT * FROM todo_table")
    fun getAllTodoItems() : LiveData<List<Todo>>

    @Query("SELECT * FROM todo_table where isCompleted = :isCompleted")
    fun getTodoItems(isCompleted: Boolean) : LiveData<List<Todo>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(todo: Todo)

    @Delete
    suspend fun delete(todo: Todo)

    @Update
    suspend fun update(todo: Todo)
}