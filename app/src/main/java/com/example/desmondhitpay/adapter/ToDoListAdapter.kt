package com.example.desmondhitpay.adapter

import android.view.ContextMenu
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.desmondhitpay.databinding.ListTodoItemBinding
import com.example.desmondhitpay.fragment.ToDoListFragment.Companion.CONTEXT_MENU_TYPE_MANAGE
import com.example.desmondhitpay.fragment.ToDoListFragment.Companion.MENU_ARRAY_MANAGE
import com.example.desmondhitpay.model.ToDoItem

class ToDoListAdapter: PagingDataAdapter<ToDoItem, ToDoListAdapter.ViewHolder>(ToDoListDiffCallback()) {

    private var selectedPosition = -1

    class ToDoListDiffCallback : DiffUtil.ItemCallback<ToDoItem>() {
        override fun areItemsTheSame(oldItem: ToDoItem, newItem: ToDoItem): Boolean =
            oldItem.rowId == newItem.rowId

        override fun areContentsTheSame(oldItem: ToDoItem, newItem: ToDoItem): Boolean =
            oldItem == newItem
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return ViewHolder(
            ListTodoItemBinding.inflate(inflater, parent, false),
            initOnLongClickListener()
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        getItem(position)?.let {
            holder.bind(it)
        }
    }

    fun getSelectedItem(): ToDoItem? {
        if (selectedPosition == -1) return null
        return getItem(selectedPosition)
    }

    private fun initOnLongClickListener(): (Int) -> Unit {
        return { position ->
            selectedPosition = position
        }
    }

    inner class ViewHolder(
        private val binding: ListTodoItemBinding,
        private val onLongClickListener: (Int) -> Unit
    ) : RecyclerView.ViewHolder(binding.root), View.OnCreateContextMenuListener {

        init {
            binding.root.apply {
                setOnLongClickListener {
                    onLongClickListener.invoke(absoluteAdapterPosition)
                    false
                }

                setOnCreateContextMenuListener(this@ViewHolder)
            }
        }

        fun bind(item: ToDoItem) {
            binding.apply {
                titleTextview.text = item.title
                description.text = item.desc
                timestampTextview.text = item.timeStamp

                root.tag = item
            }
        }

        override fun onCreateContextMenu(
            menu: ContextMenu?,
            v: View?,
            menuInfo: ContextMenu.ContextMenuInfo?
        ) {
            (binding.root.tag as? ToDoItem)?.apply {
                MENU_ARRAY_MANAGE.forEach { menuItem ->
                    menu?.add(
                        CONTEXT_MENU_TYPE_MANAGE,
                        0,
                        0,
                        binding.root.context.getString(menuItem)
                    )
                }
            }
        }
    }
}
