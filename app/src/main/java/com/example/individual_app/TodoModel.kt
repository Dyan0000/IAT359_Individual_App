package com.example.individual_app

class TodoModel
{
    companion object Factory {
        fun createList(): TodoModel = TodoModel()
    }

    var id: String = ""
    var todoTitle: String? = null
    var todoContent: String? = null
    var todoTagString: String? = null
    var todoTagInt: Int? = null
    var ifDone: Boolean? = false
}