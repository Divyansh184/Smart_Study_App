package com.example.studysmart.components


import android.icu.text.CaseMap.Title
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.modifier.modifierLocalConsumer
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.example.studysmart.data.Subject

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DeleteDialog(
    isOpen:Boolean,
    title:String,
    BodyText:String,
    onDismissReq:()->Unit,
    onConfirmButtonClicked:()->Unit
){

    if(isOpen){
        AlertDialog(onDismissRequest = { /*TODO*/ },
            title = { Text(text = title) },
            text = {
                Text(text = BodyText)
                },
            confirmButton ={
                TextButton(onClick =onDismissReq) {
                    Text(text = "Delete")
                }
            },
            dismissButton = {
                TextButton(onClick = onConfirmButtonClicked) {
                    Text(text = "Cancel")
                }
            })
    }

}