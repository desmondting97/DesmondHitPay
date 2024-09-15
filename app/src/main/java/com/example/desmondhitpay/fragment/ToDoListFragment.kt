package com.example.desmondhitpay.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.desmondhitpay.R
import com.example.desmondhitpay.adapter.ToDoListAdapter
import com.example.desmondhitpay.databinding.FragmentFirstBinding
import com.example.desmondhitpay.model.ToDoItem
import com.google.android.material.appbar.MaterialToolbar

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class ToDoListFragment : BaseFragment() {

    private var _binding: FragmentFirstBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private val toDoListAdapter by lazy { ToDoListAdapter() }

    companion object {
        const val CONTEXT_MENU_TYPE_MANAGE = 1
        val MENU_ARRAY_MANAGE = arrayOf(R.string.delete)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentFirstBinding.inflate(inflater, container, false)

        requireActivity().findViewById<MaterialToolbar>(R.id.toolbar)?.title = "To Do List"

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {
            recyclerView.apply {
                layoutManager = LinearLayoutManager(requireContext())
                adapter = toDoListAdapter
            }

            btnPrepopulate.setOnClickListener {
                toDoListViewModel.prepopulateToDoListDatabase()
            }

            btnDeleteAll.setOnClickListener {
                toDoListViewModel.dropToDoListDatabase()
            }

            fabAddItem.setOnClickListener {
                findNavController().navigate(R.id.action_ToDoListFragment_to_AddItemFragment)
            }
        }

//        toDoListViewModel.toDoListLiveData.observe(
//            viewLifecycleOwner
//        ){ toDoListAdapter.setItems(it)}

        toDoListViewModel.getToDoListItems.observe(
            viewLifecycleOwner
        ) {
            toDoListAdapter.submitData(viewLifecycleOwner.lifecycle, it)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        when (item.title) {
            getString(MENU_ARRAY_MANAGE[0]) -> {
                handleItemDelete(toDoListAdapter.getSelectedItem())
            }
        }
        return true
    }

    private fun handleItemDelete(item: ToDoItem?) {
        item?.let {
            toDoListViewModel.deleteToDoListItem(it.columnID)
        }
    }
}
