package com.example.desmondhitpay.database

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.desmondhitpay.model.ToDoItem
import kotlinx.coroutines.flow.Flow

@Dao
interface ToDoItemDao {

    @Query("SELECT * FROM todolist_table")
    fun getAllToDoItems(): PagingSource<Int, ToDoItem>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(toDoItem: ToDoItem)

    @Delete
    suspend fun delete(toDoItem: ToDoItem)

    @Query("DELETE FROM todolist_table")
    suspend fun deleteAll()
}