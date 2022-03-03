package com.example.individual_app

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager.widget.PagerAdapter
import androidx.viewpager.widget.ViewPager
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class MainActivity : AppCompatActivity()
{
    val myViews: Array<Int> = arrayOf(R.layout.todo, R.layout.done)

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

//        val viewpager = findViewById<ViewPager2>(R.id.viewpager)
//        viewpager.adapter = MyViewpager2Adapter (myViews, this)
        val viewpager = findViewById<ViewPager>(R.id.viewpager)
        viewpager.adapter = MyViewpagerAdapter (myViews, this)

        val tabLayout = findViewById<TabLayout>(R.id.tab_layout)
        // Link the TabLayout to ViewPager2
//        TabLayoutMediator(tabLayout, viewpager) { tab, position ->
//            tab.text = "OBJECT ${(position + 1)}"
//        }.attach()

        // show the tab icon
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




