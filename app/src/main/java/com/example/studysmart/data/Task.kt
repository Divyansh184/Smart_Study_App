package com.example.studysmart.data

data class Task(
    val title:String,
    val desc:String,
    val dueDate:Long,
    val priority:Int,
    val relToSub:String,
    val isComplete:Boolean,
    val taskSubID:Int,
    val taskID:Int
    )
