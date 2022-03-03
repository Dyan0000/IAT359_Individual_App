package com.example.individual_app

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.google.android.material.floatingactionbutton.FloatingActionButton

class TodoActivity : AppCompatActivity()
{
    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.todo)

        // Click the FAB to Add a New Task
        val fab = findViewById<FloatingActionButton>(R.id.fab)

        fab.setOnClickListener {
            val intent = Intent(this,
                AddNewTasks::class.java)
            startActivity(intent)
        }

    }
}