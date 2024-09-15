package com.example.desmondhitpay.datasource

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.desmondhitpay.model.ToDoItem
import com.example.desmondhitpay.repository.ToDoListRepository

class ToDoItemDataSource (
    private val repository: ToDoListRepository
): PagingSource<Int, ToDoItem>() {

    companion object {
        private const val STARTING_PAGE_INDEX = 1
    }

    override fun getRefreshKey(state: PagingState<Int, ToDoItem>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1) ?:
            state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, ToDoItem> {
        val position = params.key ?: STARTING_PAGE_INDEX
        return LoadResult.Page(data = repository.fetchAllToDoItems(),
            prevKey = if (position == STARTING_PAGE_INDEX) null else -1,
            nextKey = if (repository.fetchAllToDoItems().isEmpty()) null else position + 1)
    }
}