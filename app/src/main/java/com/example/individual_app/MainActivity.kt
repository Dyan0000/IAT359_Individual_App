package com.example.individual_app

import android.app.Dialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.viewpager.widget.ViewPager
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.tabs.TabLayout
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class MainActivity : AppCompatActivity()
{
    val myViews: Array<Int> = arrayOf(R.layout.fragment_todo_list, R.layout.fragment_done_list)
    lateinit var database: DatabaseReference

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

        // set up view pager
//        val viewpager = findViewById<ViewPager2>(R.id.viewpager)
//        viewpager.adapter = MyViewpager2Adapter (myViews, this)
        val viewpager = findViewById<ViewPager>(R.id.viewpager)
        viewpager.adapter = MyViewpagerAdapter (myViews, this)

//        if (viewpager.adapter != null)
//        {
//            supportFragmentManager
//                .beginTransaction()
//                .add(R.id.todo_list,
//                    TodoListFragment.newInstance(),
//                    "todo_list").commit()
//        }

        val tabLayout = findViewById<TabLayout>(R.id.tab_layout)
        // Link the TabLayout to ViewPager2
//        TabLayoutMediator(tabLayout, viewpager) { tab, position ->
//            tab.text = "OBJECT ${(position + 1)}"
//        }.attach()

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

        // Link the TabLayout to ViewPager
        tabLayout.setupWithViewPager(viewpager)

    }

}




