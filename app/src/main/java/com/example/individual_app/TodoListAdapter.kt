package com.example.individual_app

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class TodoListViewHolder (inflater: LayoutInflater,
                          parent: ViewGroup) :
        RecyclerView.ViewHolder (inflater.inflate(R.layout.row_number,
                                                  parent,
                                      false))

internal class TodoListAdapter (private val array: Array<Int>):
                                        RecyclerView.Adapter<TodoListViewHolder> ()
{
    override fun getItemCount(): Int = array.size

    override fun onCreateViewHolder(parent: ViewGroup,
                                    viewType: Int): TodoListViewHolder
    {
        val inflater = LayoutInflater.from(parent.context)
        return TodoListViewHolder(inflater, parent)
    }

    override fun onBindViewHolder(holder: TodoListViewHolder,
                                  position: Int)
    {
        (holder.itemView as TextView).text = array[position].toString()
    }

}