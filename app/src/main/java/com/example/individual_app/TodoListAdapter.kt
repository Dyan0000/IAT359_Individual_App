package com.example.individual_app

import android.app.Dialog
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.textfield.TextInputLayout
import com.google.firebase.database.FirebaseDatabase

class TodoListAdapter (private val taskList: MutableList<TodoModel>,
                       private val context : Context):
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

        // only show checkbox when it is unselected
        if (isDone) {
            holder.todoCheck.visibility = View.GONE // hide
        } else {
            holder.todoCheck.visibility = View.VISIBLE // show
        }

        // when checkbox is selected...
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

        // display a corresponding tag
        when (holder.todoTag.id)
        {
            2131296479 -> holder.todoTag.setImageResource(R.drawable.icon_home)
            2131296777 -> holder.todoTag.setImageResource(R.drawable.icon_study)
            2131296778 -> holder.todoTag.setImageResource(R.drawable.icon_work)
        }

        // edit an existing task
        // 1. update new changes to Firebase
        // 2. delete current task
        holder.todoEdit.setOnClickListener {
            Log.d("EditTask", "Start to edit * ${holder.todoTitle.text} *.")

            val alertDialog = Dialog(context)
            alertDialog.setCancelable(false)
            alertDialog.setContentView(R.layout.edit_task)

            val title = alertDialog.findViewById<TextInputLayout>(R.id.taskTitle)
            val description = alertDialog.findViewById<TextInputLayout>(R.id.taskDescription)
            val checkedTag = alertDialog.findViewById<RadioGroup>(R.id.tags)

            // show the task detail on the dialog
            title.editText?.setText(holder.todoTitle.text)
            description.editText?.setText(holder.todoContent.text)
            checkedTag.check(holder.todoTag.id)

            // access the Firebase
            val firebase = FirebaseDatabase.getInstance().getReference("To-do List")
            val data = firebase.child(dataItem.id)

            // send the task changes to Firebase
            alertDialog.findViewById<Button>(R.id.changeTask)
                .setOnClickListener {

                    val newTodoItemData = TodoModel.createList()
                    newTodoItemData.id = dataItem.id
                    newTodoItemData.todoTitle = title.editText?.text.toString()
                    newTodoItemData.todoContent = description.editText?.text.toString()
                    newTodoItemData.todoTag = checkedTag.checkedRadioButtonId
                    newTodoItemData.ifDone = holder.todoCheck.isChecked

                    data.setValue(newTodoItemData)

                    alertDialog.dismiss()
                    Toast.makeText(context,
                        "Task has been changed.",
                        Toast.LENGTH_LONG).show()
                }

            // delete current task
            alertDialog.findViewById<TextView>(R.id.deleteTask)
                .setOnClickListener {

                    // remove this task from the Firebase
                    data.removeValue()
                    this.notifyDataSetChanged()

                    alertDialog.dismiss()
                    Toast.makeText(context,
                        "Task has been deleted.",
                        Toast.LENGTH_LONG).show()
                }

            // cancel the dialog
            alertDialog.findViewById<Button>(R.id.changeCancel)
                .setOnClickListener {
                    alertDialog.dismiss()
                }

            alertDialog.show()
        }
    }

    class TodoListViewHolder (itemView : View) : RecyclerView.ViewHolder (itemView)
    {
        val todoTitle : TextView = itemView.findViewById<TextView>(R.id.itemTitle)
        val todoContent : TextView = itemView.findViewById<TextView>(R.id.itemContent)
        val todoTag : ImageView = itemView.findViewById<ImageView>(R.id.itemTag)
        val todoCheck: CheckBox = itemView.findViewById<CheckBox>(R.id.itemCheck)
        val todoEdit : LinearLayout = itemView.findViewById<LinearLayout>(R.id.itemEdit)
    }

}