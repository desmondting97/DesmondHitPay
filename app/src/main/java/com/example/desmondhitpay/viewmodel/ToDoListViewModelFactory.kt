package com.example.desmondhitpay.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.desmondhitpay.repository.ToDoListRepository

class ToDoListViewModelFactory(
    private val toDoListRepository: ToDoListRepository
): ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {

        if (modelClass.isAssignableFrom(ToDoListViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return ToDoListViewModel(toDoListRepository) as T
        }

        throw IllegalArgumentException("Unknown ViewModel class")
    }
}