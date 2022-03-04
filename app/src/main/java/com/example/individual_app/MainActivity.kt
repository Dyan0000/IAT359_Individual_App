package com.example.individual_app

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.viewpager.widget.ViewPager
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.tabs.TabLayout

class MainActivity : AppCompatActivity()
{
    val myViews: Array<Int> = arrayOf(R.layout.fragment_todo_list, R.layout.fragment_done_list)

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Click the FloatingActionButton to enter the New Task screen
        val fab = findViewById<View>(R.id.fab) as FloatingActionButton

        fab.setOnClickListener {
//            val intent = Intent(this,
//                AddNewTasks::class.java)
//            startActivity(intent)
            val alertDialog = AlertDialog.Builder(this)
            val textEditText = EditText(this)
            alertDialog.setTitle("Add a New Task")
            alertDialog.setMessage("Enter task details here.")
            alertDialog.setView(textEditText)
            alertDialog.show()
        }

//        val viewpager = findViewById<ViewPager2>(R.id.viewpager)
//        viewpager.adapter = MyViewpager2Adapter (myViews, this)
        val viewpager = findViewById<ViewPager>(R.id.viewpager)
        viewpager.adapter = MyViewpagerAdapter (myViews, this)

//        if ()
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




