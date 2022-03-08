package com.example.individual_app

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
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

abstract class TodoListAdapter (private val todoList: MutableList<TodoModel>):
                      RecyclerView.Adapter<TodoListAdapter.TodoListViewHolder> ()
{
    private val updateTodoList : UpdateTodoList

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
        holder.todoCheck.isChecked = dataItem.ifDone

        holder.todoCheck.setOnClickListener {
            updateTodoList.modifyItem(dataItem.id, !dataItem.ifDone)
        }

        when (holder.todoTag.id)
        {
            2131296479 -> holder.todoTag.setImageResource(R.drawable.icon_home)
            2131296777 -> holder.todoTag.setImageResource(R.drawable.icon_study)
            2131296778 -> holder.todoTag.setImageResource(R.drawable.icon_work)
        }
    }

    class TodoListViewHolder (itemView : View) : RecyclerView.ViewHolder (itemView)
    {
        val todoTitle: TextView = itemView.findViewById<TextView>(R.id.itemTitle)
        val todoContent: TextView = itemView.findViewById<TextView>(R.id.itemContent)
        val todoTag: ImageView = itemView.findViewById<ImageView>(R.id.itemTag)
        val todoCheck: CheckBox = itemView.findViewById<CheckBox>(R.id.itemCheck)
    }

}