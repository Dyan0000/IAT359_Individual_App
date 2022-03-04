package com.example.individual_app

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.google.android.material.floatingactionbutton.FloatingActionButton

class TodoActivity : AppCompatActivity()
{
    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.todo)

        // Click the FloatingActionButton to enter the New Task screen
        val fab = findViewById<View>(R.id.fab) as FloatingActionButton

        fab.setOnClickListener { view ->
//            Toast.makeText(this,
//                "FAB clicked!", Toast.LENGTH_LONG).show()
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

        val testButton = findViewById<Button>(R.id.testButton)
        testButton.setOnClickListener {
            val intent = Intent(this,
                AddNewTasks::class.java)
            startActivity(intent)
        }

    }
}