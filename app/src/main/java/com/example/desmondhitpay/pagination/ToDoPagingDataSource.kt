package com.example.desmondhitpay.pagination

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.desmondhitpay.database.ToDoItemDao
import com.example.desmondhitpay.model.ToDoItem

class ToDoPagingDataSource(
    private val dao: ToDoItemDao
) : PagingSource<Int, ToDoItem>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, ToDoItem> {
        val page = params.key ?: 0

        return try {
            Log.d("ToDoPagingDataSource", "load: $page")
            val entities = dao.getPaged(params.loadSize, page * params.loadSize)
            LoadResult.Page(
                data = entities,
                prevKey = if (page == 0) null else page - 1,
                nextKey = if (entities.isEmpty()) null else page + 1
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, ToDoItem>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }
}