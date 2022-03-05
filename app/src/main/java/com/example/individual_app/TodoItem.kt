package com.example.individual_app

data class TodoItem(var id : String? = null,
                    var todoTitle : String? = null,
                    var todoContent : String? = null,
                    var todoTag : Int? = null,
                    var ifDone : Boolean? = null)
