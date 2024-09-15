package com.example.desmondhitpay.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.desmondhitpay.viewmodel.ToDoListViewModel

abstract class BaseFragment: Fragment() {
    protected lateinit var toDoListViewModel: ToDoListViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        toDoListViewModel = ViewModelProvider(requireActivity())[
                ToDoListViewModel::class.java
        ]
    }
}