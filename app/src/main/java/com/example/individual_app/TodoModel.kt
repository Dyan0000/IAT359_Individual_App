package com.example.individual_app

class TodoModel
{
    companion object Factory {
        fun createList(): TodoModel = TodoModel()
    }

    var id: String? = null
    var todoTitle: String? = null
//    var todoContent: String? = null
//    var todoDate: String? = null
//    var todoTag: Int? = null
    var ifDone: Boolean? = false
}