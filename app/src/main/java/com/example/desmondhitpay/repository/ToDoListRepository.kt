package com.example.desmondhitpay.repository

import androidx.annotation.WorkerThread
import androidx.paging.InvalidatingPagingSourceFactory
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.desmondhitpay.database.ToDoItemDao
import com.example.desmondhitpay.model.ToDoItem
import com.example.desmondhitpay.pagination.ToDoPagingDataSource
import kotlinx.coroutines.flow.Flow
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.Random

const val PAGE_SIZE = 10

class ToDoListRepository(private val toDoItemDao: ToDoItemDao) {

    private val pagingSourceInvalidatingFactory = InvalidatingPagingSourceFactory {
        ToDoPagingDataSource(toDoItemDao)
    }

    private val pager = Pager(
        config = PagingConfig(
            pageSize = PAGE_SIZE,
            enablePlaceholders = false,
            initialLoadSize = PAGE_SIZE
        ),
        pagingSourceFactory = pagingSourceInvalidatingFactory
    )

    @WorkerThread
    fun getPagedToDoItems(): Flow<PagingData<ToDoItem>> {
        return pager.flow
    }

    @WorkerThread
    suspend fun addToDoItem(item: ToDoItem) {
        toDoItemDao.insert(item)
        pagingSourceInvalidatingFactory.invalidate()

    }

    suspend fun deleteToDoItem(item: ToDoItem) {
        toDoItemDao.delete(item)
        pagingSourceInvalidatingFactory.invalidate()
    }

    @WorkerThread
    suspend fun dropToDoDatabase() {
        toDoItemDao.deleteAll()
        pagingSourceInvalidatingFactory.invalidate()
    }

    @WorkerThread
    suspend fun prePopulateToDoList() {

        val dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH)
        val date = dateFormat.format(Date())

        val items = mutableListOf<ToDoItem>()
        for (i in 0..1999) {
            items.add(
                ToDoItem(
                    title = generateRandomString(),
                    desc = generateRandomString(),
                    timeStamp = date
                )
            )
        }
        items.forEach { item ->
            toDoItemDao.insert(item)
        }
        pagingSourceInvalidatingFactory.invalidate()
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
}