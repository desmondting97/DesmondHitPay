package com.example.desmondhitpay.repository

import androidx.annotation.WorkerThread
import com.example.desmondhitpay.database.ToDoItemDao
import com.example.desmondhitpay.model.ToDoItem
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Random

class ToDoListRepository(private val toDoItemDao: ToDoItemDao) {

    val PAGE_SIZE = 10

    @WorkerThread
    suspend fun allToDoItems(): List<ToDoItem> = toDoItemDao.getAllToDoItems()

    @WorkerThread
    suspend fun addToDoItem(item: ToDoItem) = toDoItemDao.insert(item)

    suspend fun deleteToDoItem(item: ToDoItem) = toDoItemDao.delete(item)

    @WorkerThread
    suspend fun dropToDoDatabase() = toDoItemDao.deleteAll()

    @WorkerThread
    suspend fun prePopulateToDoList() {

        val dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
        val date = dateFormat.format(Date())

        val items = mutableListOf<ToDoItem>()
        for (i in 0..1999) {
            items.add(
                ToDoItem(
                    rowId = i,
                    title = generateRandomString(),
                    desc = generateRandomString(),
                    timeStamp = date
                )
            )
        }
        items.forEach { item ->
            toDoItemDao.insert(item)
        }
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

//    fun getToDoList(): LiveData<PagingData<ToDoItem>> = Pager(
//        config = PagingConfig(pageSize = PAGE_SIZE, enablePlaceholders = false),
//        pagingSourceFactory = {ToDoItemDataSource(this)}
//    ).liveData

}