package com.example.individual_app

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class TodoListFragment : Fragment() {

    private lateinit var recyclerView : RecyclerView
//    private lateinit var todoList : ArrayList<TodoItem>
    private lateinit var todoList : MutableList<TodoModel>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val fragment =  inflater.inflate(R.layout.recyclerview_todolist,
                                container,
                                false)
        recyclerView = fragment.findViewById<RecyclerView>(R.id.todo_list)
        recyclerView.layoutManager = LinearLayoutManager (activity as Context)
        recyclerView.setHasFixedSize(true)
        Log.d("RefreshButton", "~ inflate the fragment layout ~")

//        var todolist:Array<Int> = arrayOf(1,2,3,4,5,6,7,8,9,10,11,12)
//        todoList = arrayListOf<TodoItem>()
        todoList = mutableListOf<TodoModel>()
        getTodoListData()
        recyclerView.adapter = TodoListAdapter(todoList)

        return fragment
    }

    private fun getTodoListData()
    {
        Log.d("RefreshButton", "~ start getting data ~")
        MainActivity().database = FirebaseDatabase.getInstance().getReference("To-do List")
        Log.d("RefreshButton", "~ get reference ~")

        MainActivity().database.addValueEventListener(object : ValueEventListener
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

    companion object
    {
        fun newInstance(): TodoListFragment
        {
            return TodoListFragment()
        }

    }
}