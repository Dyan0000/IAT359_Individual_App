package com.example.individual_app

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class TodoListFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val fragment =  inflater.inflate(R.layout.fragment_todo_list,
                                container,
                                false)
        val recyclerView = fragment.findViewById<RecyclerView>(R.id.todo_list)
        recyclerView.layoutManager = LinearLayoutManager (activity as Context)

        var todolist:Array<Int> = arrayOf(1,2,3)
        recyclerView.adapter = TodoListAdapter(todolist)
        return fragment
    }

    companion object
    {
        fun newInstance(): TodoListFragment
        {
            return TodoListFragment()
        }

    }
}