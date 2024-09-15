package com.example.desmondhitpay.adapter

import android.annotation.SuppressLint
import android.view.ContextMenu
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.desmondhitpay.fragment.ToDoListFragment.Companion.CONTEXT_MENU_TYPE_MANAGE
import com.example.desmondhitpay.fragment.ToDoListFragment.Companion.MENU_ARRAY_MANAGE
import com.example.desmondhitpay.databinding.ListTodoItemBinding
import com.example.desmondhitpay.model.ToDoItem

class ToDoListAdapter(

): PagingDataAdapter<ToDoItem, ToDoListAdapter.ViewHolder>(ToDoListDiffCallback()) {

    private val items = arrayListOf<ToDoItem>()
    private var selectedPosition = -1
    private val differ = AsyncListDiffer(this, ToDoListDiffCallback())

    class ToDoListDiffCallback: DiffUtil.ItemCallback<ToDoItem>() {
        override fun areItemsTheSame(oldItem: ToDoItem, newItem: ToDoItem): Boolean = oldItem.columnID == newItem.columnID

        override fun areContentsTheSame(oldItem: ToDoItem, newItem: ToDoItem): Boolean = oldItem == newItem
    }


    fun submitList(list: List<ToDoItem>) {
        differ.submitList(list)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return ViewHolder(
            ListTodoItemBinding.inflate(inflater, parent, false),
            initOnLongClickListener()
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items[position]
        holder.bind(item)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setItems(newItems: List<ToDoItem>) {
        items.clear()
        items.addAll(newItems)
        notifyDataSetChanged()
    }

    fun getSelectedItem(): ToDoItem? {
        if (selectedPosition == -1) return null
        return items[selectedPosition]
    }

    private fun initOnLongClickListener(): (Int) -> Unit {
        return { position ->
            selectedPosition = position
        }
    }

    class ViewHolder(
        private val binding: ListTodoItemBinding,
        private val onLongClickListener: (Int) -> Unit
    ): RecyclerView.ViewHolder(binding.root), View.OnCreateContextMenuListener {

        init {
            binding.root.apply {
                setOnLongClickListener {
                    (tag as? ToDoItem)?.position = adapterPosition
                    onLongClickListener.invoke(adapterPosition)
                    false
                }

                setOnCreateContextMenuListener(this@ViewHolder)
            }
        }

        fun bind(item: ToDoItem) {
            binding.apply {
                titleTextview.text = item.title
                description.text = item.description
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
