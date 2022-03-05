package com.example.individual_app

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

//class TodoListViewHolder (inflater: LayoutInflater,
//                          parent: ViewGroup) :
//        RecyclerView.ViewHolder (inflater.inflate(R.layout.row_number,
//                                                  parent,
//                                                  false))
//
//internal class TodoListAdapter (private val array: Array<Int>):
//                                        RecyclerView.Adapter<TodoListViewHolder> ()
//{
//    override fun getItemCount(): Int = array.size
//
//    override fun onCreateViewHolder(parent: ViewGroup,
//                                    viewType: Int): TodoListViewHolder
//    {
//        val inflater = LayoutInflater.from(parent.context)
//        return TodoListViewHolder(inflater, parent)
//    }
//
//    override fun onBindViewHolder(holder: TodoListViewHolder,
//                                  position: Int)
//    {
//        (holder.itemView as TextView).text = array[position].toString()
//    }
//
//}

class TodoListAdapter (private val todoList: MutableList<TodoModel>):
                      RecyclerView.Adapter<TodoListAdapter.TodoListViewHolder> ()
{

    override fun getItemCount(): Int = todoList.size

    override fun onCreateViewHolder(parent: ViewGroup,
                                    viewType: Int): TodoListViewHolder
    {
        val inflater = LayoutInflater.from(parent.context).inflate(R.layout.row_todo_item,
                                                                   parent,
                                                                   false)
        Log.d("Adapter", "~ I'm in onCreateViewHolder ~")
        return TodoListViewHolder(inflater)
    }

    override fun onBindViewHolder(holder: TodoListViewHolder,
                                  position: Int)
    {
        Log.d("Adapter", "~ I'm in onBindViewHolder ~")
        // (holder.itemView as TextView).text = arraylist[position].toString()

        val dataItem = todoList[position]
        holder.todoTitle.text = dataItem.todoTitle
        holder.todoContent.text = dataItem.todoContent
        holder.todoTag.id = dataItem.todoTag!!
    }

    class TodoListViewHolder (itemView : View) : RecyclerView.ViewHolder (itemView)
    {
        val todoTitle = itemView.findViewById<TextView>(R.id.itemTitle)
        val todoContent = itemView.findViewById<TextView>(R.id.itemContent)
        val todoTag = itemView.findViewById<ImageView>(R.id.itemTag)
    }

}