package com.example.individual_app

import android.app.Dialog
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.tabs.TabLayout
import com.google.android.material.textfield.TextInputLayout
import com.google.firebase.database.*

class MainActivity : AppCompatActivity(), UpdateTodoList
{
//    val myViews: Array<Int> = arrayOf(R.layout.recyclerview_todolist, R.layout.recyclerview_donelist)

    lateinit var database: DatabaseReference
    lateinit var databaseRetrieve: DatabaseReference

    lateinit var recyclerView : RecyclerView
    lateinit var todoList : MutableList<TodoModel>

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        databaseRetrieve = FirebaseDatabase.getInstance().getReference("To-do List")

        // click the FloatingActionButton to add a new task
        val fab = findViewById<View>(R.id.fab) as FloatingActionButton
        database = FirebaseDatabase.getInstance().reference
        fab.setOnClickListener {
//            val alertDialog = AlertDialog.Builder(this)
//            val textInput = EditText(this)
//            alertDialog.setTitle("Add a New Task")
//            alertDialog.setMessage("Enter task details here.")
//            alertDialog.setView(textInput)
//            alertDialog.setPositiveButton("ADD") { dialog, _ ->
//                val todoItemData = TodoModel.createList()
//                todoItemData.todoTitle = textInput.text.toString()
//                todoItemData.ifDone = false
//
//                val newItemData = database.child("To-do List").push()
//                todoItemData.id = newItemData.key
//                newItemData.setValue(todoItemData)
//
//                dialog.dismiss()
//                Toast.makeText(this,
//                    "New task has been added.",
//                    Toast.LENGTH_LONG).show()
//            }
//            alertDialog.setNegativeButton("CANCEL") { dialog, _ ->
//                dialog.dismiss()
//            }
//            alertDialog.show()

            val alertDialog = Dialog(this)
            alertDialog.setCancelable(false)                        // unable to cancel the dialog by clicking anywhere outside the dialog
            alertDialog.setContentView(R.layout.new_task_inputs)    // set a layout as the custom dialog

            // submit the details of a new task to Firebase
            alertDialog.findViewById<Button>(R.id.submitButton)
                .setOnClickListener {
                    val todoItemData = TodoModel.createList()

                    val title = alertDialog.findViewById<TextInputLayout>(R.id.titleField)
                    todoItemData.todoTitle = title.editText?.text.toString()
                    Log.d("todoItemData", "title: ${todoItemData.todoTitle}")

                    val description = alertDialog.findViewById<TextInputLayout>(R.id.descriptionField)
                    todoItemData.todoContent = description.editText?.text.toString()
                    Log.d("todoItemData", "description: ${todoItemData.todoContent}")

                    val checkedTag = alertDialog.findViewById<RadioGroup>(R.id.tags).checkedRadioButtonId
                    todoItemData.todoTag = checkedTag
                    Log.d("todoItemData", "tag: ${todoItemData.todoTag}")

                    todoItemData.ifDone = false

                    // send data to Firebase
                    val newItemData = database.child("To-do List").push()
                    todoItemData.id = newItemData.key
                    newItemData.setValue(todoItemData)

                    alertDialog.dismiss()
                    Toast.makeText(this,
                        "New task has been added.",
                        Toast.LENGTH_LONG).show()
                }

            // cancel the dialog
            alertDialog.findViewById<Button>(R.id.cancelButton)
                .setOnClickListener {
                    alertDialog.dismiss()
                }

            alertDialog.show()
        }

        val tabLayout = findViewById<TabLayout>(R.id.tab_layout)
        tabLayout.addOnTabSelectedListener(object: TabLayout.OnTabSelectedListener
        {
            override fun onTabSelected(tab: TabLayout.Tab?)
            {
                tabLayout.getTabAt(0)!!.setIcon(R.drawable.icon_todo)
                tabLayout.getTabAt(1)!!.setIcon(R.drawable.icon_done)
            }
            override fun onTabUnselected(tab: TabLayout.Tab?)
            {
                tabLayout.getTabAt(0)!!.setIcon(R.drawable.icon_todo)
                tabLayout.getTabAt(1)!!.setIcon(R.drawable.icon_done)
            }
            override fun onTabReselected(tab: TabLayout.Tab?) {}
        })

        // show to-do items in FrameLayout R.id.container
        recyclerView = findViewById<RecyclerView>(R.id.todo_list)
        recyclerView.layoutManager = LinearLayoutManager (this)
        todoList = mutableListOf<TodoModel>()
        getTodoListData()

        // change the ifDone to true if checked


    }

    private fun getTodoListData()
    {
//        databaseRetrieve = FirebaseDatabase.getInstance().getReference("To-do List")
        Log.d("RetrieveData", "get reference")

        databaseRetrieve.addValueEventListener(object : ValueEventListener
        {
            override fun onDataChange(snapshot: DataSnapshot)
            {
                Log.d("RetrieveData", snapshot.value.toString())
                todoList.clear()

                if (snapshot.exists())
                {
                    Log.d("RetrieveData", "if snapshot exists: ${snapshot.exists().toString()}")
                    for (each in snapshot.children)
                    {
                        Log.d("RetrieveData", "each: ${each.value.toString()}")

                        val todoItemData = each.getValue(TodoModel::class.java)
                        Log.d("RetrieveData", "todoItemData: ${todoItemData.toString()}")

                        if (todoItemData != null && todoItemData.ifDone == false) {
                            todoList.add(todoItemData)
                        }
                    }
                } // end of if (snapshot.exists())

                recyclerView.adapter = TodoListAdapter(todoList)
            } // end of onDataChange

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })
    }

    override fun modifyItem(itemID: String?, ifDone: Boolean) {
        val dataItem = itemID?.let { databaseRetrieve.child(it) }
        dataItem?.child("ifDone")?.setValue(ifDone)
    }

}




