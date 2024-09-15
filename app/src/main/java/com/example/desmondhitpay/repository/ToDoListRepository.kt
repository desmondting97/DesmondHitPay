package com.example.desmondhitpay.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.lifecycleScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.liveData
import com.example.desmondhitpay.database.ToDoDBAdapter
import com.example.desmondhitpay.datasource.ToDoItemDataSource
import com.example.desmondhitpay.model.ToDoItem
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Random

class ToDoListRepository(private val toDoDbAdapter: ToDoDBAdapter) {

    val NETWORK_PAGE_SIZE = 10

    fun addToDoItem(item: ToDoItem) = toDoDbAdapter.addToDoItem(item)

    fun deleteToDoItem(rowID: String) = toDoDbAdapter.deleteToDoItem(rowID)

    fun fetchAllToDoItems() = toDoDbAdapter.fetchAllToDoItems()

    fun dropToDoDatabase() = toDoDbAdapter.dropToDoDatabase()

    fun prePopulateToDoList() {

        val dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
        val date = dateFormat.format(Date())

        val items = mutableListOf<ToDoItem>()
        for (i in 0..1999) {
            items.add(ToDoItem(title = generateRandomString(), description = generateRandomString(), timeStamp = date))
        }
        toDoDbAdapter.addToDoItems(items)
    }

    private fun generateRandomString(): String {
        val random = Random()
        val chars = "0123456789abcdefghijklmnopqrstuvwxyz"
        val charArray = CharArray(10)

        for (i in 0 until 10) {
            charArray[i] = chars[random.nextInt(chars.length)]
        }

        return String(charArray)
    }

    fun getToDoList(): LiveData<PagingData<ToDoItem>> = Pager(
        config = PagingConfig(pageSize = NETWORK_PAGE_SIZE, enablePlaceholders = false),
        pagingSourceFactory = {ToDoItemDataSource(this)}
    ).liveData

}