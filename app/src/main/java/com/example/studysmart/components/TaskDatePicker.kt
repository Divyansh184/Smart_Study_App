package com.example.studysmart.components

import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DatePickerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TaskDatePiker(
    state:DatePickerState,
    isOpen:Boolean,
    confirmButtonText:String="Ok",
    deleteButtonText:String="Cancel",
    onDismissRequest:()->Unit,
    onConfirmButtonCLicked:()->Unit
){
    if(isOpen){
    DatePickerDialog(
        onDismissRequest = { /*TODO*/ },
        confirmButton = {
                        TextButton(onClick = onConfirmButtonCLicked) {
                            Text(text = confirmButtonText)
                        }
                        },
        dismissButton = {
            TextButton(onClick = onDismissRequest) {
                Text(text = deleteButtonText)
            }
        },
        content = { DatePicker(
            state = state,
            dateValidator = {
                val selectedDate=Instant
                    .ofEpochMilli(it)
                    .atZone(ZoneId.systemDefault())
                    .toLocalDate()
                val currentDate=LocalDate.now(ZoneId.systemDefault())
                selectedDate>=currentDate
            })})
}
}