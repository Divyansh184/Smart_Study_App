package com.example.studysmart.data

import androidx.compose.ui.graphics.Color
import com.example.studysmart.ui.theme.gradient1
import com.example.studysmart.ui.theme.gradient2
import com.example.studysmart.ui.theme.gradient3
import com.example.studysmart.ui.theme.gradient4
import com.example.studysmart.ui.theme.gradient5

data class Subject(
    val name:String,
    val goalHr:Float,
    val color:List<Color>,
    val subId:Int
){
    companion object{
        val subCardColor= listOf(gradient1, gradient2, gradient3, gradient4, gradient5)
    }
}
