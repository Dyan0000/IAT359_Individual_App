package com.example.individual_app

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.FirebaseDatabase

class TodoListAdapter (private val taskList: MutableList<TodoModel>):
                      RecyclerView.Adapter<TodoListAdapter.TodoListViewHolder> ()
{
    fun switchTo(newList: MutableList<TodoModel>) {

        for (each in taskList)
            Log.d(
                "TabBar",
                "Current taskList is ${each.todoTitle} (checked: ${each.ifDone})")

        for (each in newList)
            Log.d("TabBar",
                "newList is ${each.todoTitle} (checked: ${each.ifDone})")

        taskList.clear()
        taskList.addAll(newList)
        this.notifyDataSetChanged()
    }

    override fun getItemCount(): Int = taskList.size

    override fun onCreateViewHolder(parent: ViewGroup,
                                    viewType: Int): TodoListViewHolder
    {
        val inflater = LayoutInflater.from(parent.context).inflate(R.layout.row_todo_item,
                                                                   parent,
                                                                   false)

        return TodoListViewHolder(inflater)
    }

    override fun onBindViewHolder(holder: TodoListViewHolder,
                                  position: Int)
    {
        val dataItem = taskList[position]
        holder.todoTitle.text = dataItem.todoTitle
        holder.todoContent.text = dataItem.todoContent
        holder.todoTag.id = dataItem.todoTag!!
        holder.todoCheck.isChecked = dataItem.ifDone!!

        val isDone = holder.todoCheck.isChecked
        Log.d("TabBar", "${dataItem.todoTitle} is checked: ${dataItem.ifDone}")

        if (isDone) {
            holder.todoCheck.visibility = View.GONE // hide
        } else {
            holder.todoCheck.visibility = View.VISIBLE // show
        }

        holder.todoCheck.setOnClickListener {

            val checked = holder.todoCheck.isChecked
            Log.d(
                "TabBar",
                "CheckedChangeListener: ${holder.todoTitle.text} isChecked -> $checked"
            )

            if (checked)
            {
                // update Firebase data
                val database = FirebaseDatabase.getInstance().getReference("To-do List")
                val dataID = database.child(dataItem.id)
                dataID.child("ifDone").setValue(checked)

                // update local data
                dataItem.ifDone = checked
            }
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