package com.example.studysmart.util

import androidx.compose.ui.graphics.Color
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId
import java.time.format.DateTimeFormatter

enum class Priority(val tit:String,val color: Color,val value:Int){
    LOW(tit="low", color = Color.Green,0 ),
    MEDIUM(tit="Medium", color = Color.Yellow,1 ),
    HARD(tit="Hard", color = Color.Red,2 );

    companion object{
        fun fromInt(value: Int)= values().firstOrNull{it.value==value}?:MEDIUM
    }
}


fun Long?.changeMillisToDateString():String{
    val date: LocalDate = this?.let{
        Instant
            .ofEpochMilli(it)
            .atZone(ZoneId.systemDefault())
            .toLocalDate()
    }?:LocalDate.now()
    return date.format(DateTimeFormatter.ofPattern("dd MMM yyyy"))
}