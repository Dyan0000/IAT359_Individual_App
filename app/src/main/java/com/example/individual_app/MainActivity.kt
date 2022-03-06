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

class MainActivity : AppCompatActivity()
{
    val myViews: Array<Int> = arrayOf(R.layout.recyclerview_todolist, R.layout.fragment_donelist)

    lateinit var database: DatabaseReference
    lateinit var databaseRetrieve: DatabaseReference

//    lateinit var recyclerView : RecyclerView
    lateinit var todoList : MutableList<TodoModel>

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

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

        // Link the TabLayout to ViewPager2
//        TabLayoutMediator(tabLayout, viewpager) { tab, position ->
//            tab.text = "OBJECT ${(position + 1)}"
//        }.attach()

        // Link the TabLayout to ViewPager
//        tabLayout.setupWithViewPager(viewpager)

        // show rows of to-do items
        val recyclerView = findViewById<RecyclerView>(R.id.todo_list)
//        recyclerView.layoutManager = LinearLayoutManager (this) // should be R.id.container
        todoList = mutableListOf<TodoModel>()
//        getTodoListData()
//        recyclerView.adapter = TodoListAdapter(todoList)
    }

    private fun getTodoListData() {
        databaseRetrieve = FirebaseDatabase.getInstance().getReference("To-do List")
        databaseRetrieve.addValueEventListener(object : ValueEventListener
        {
            override fun onDataChange(snapshot: DataSnapshot)
            {
                Log.d("RefreshButton", snapshot.value.toString())
                todoList.clear()
                if (snapshot.exists())
                {
//                    for (each in snapshot.children)
//                    {
//                        val itemData = each.getValue(TodoItem::class.java)
//                        todoList.add(itemData!!)
//                    }
                    val items = snapshot.children.iterator()
                    if (items.hasNext())
                    {
                        val todoIndexedValue = items.next()
                        val itemsIterator = todoIndexedValue.children.iterator()

                        while (itemsIterator.hasNext())
                        {
                            val currentItem = itemsIterator.next()
                            val todoItemData = TodoModel.createList()
                            val map = currentItem.value as HashMap<*, *>

                            todoItemData.id = currentItem.key
                            todoItemData.todoTitle = map["todoTitle"] as String?
                            todoItemData.todoContent = map["todoContent"] as String?
                            todoItemData.todoTag = map["todoTag"] as Int?
                            todoItemData.ifDone = map["ifDone"] as Boolean?

                            todoList.add(todoItemData)
                        }
                    }
                }
                Log.d("RefreshButton", "~ done getting data ~")
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })
    }

}




