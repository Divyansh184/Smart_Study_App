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
fun AddSubjectDialog(
    isOpen:Boolean,
    title:String="Add/Update Subject",
    selectedColor:List<Color>,
    subjectName:String,
    goalHours:String,
    onSubjectNameChange:(String)->Unit,
    onGoalHourChange:(String)->Unit,
    onColorChange:(List<Color>)->Unit,
    onDismissReq:()->Unit,
    onConfirmButtonClicked:()->Unit
){
    var suberror by rememberSaveable { mutableStateOf<String?>("") }
    var hrerror by rememberSaveable { mutableStateOf<String?>("") }

    suberror=when{
        subjectName.isBlank()->"Please enter sub name"
        subjectName.length<2 ->"Please enter sub name"
        subjectName.length>20 ->"Please enter sub name"
        else ->null
    }

    hrerror=when{
        goalHours.isBlank()->"Please enter hours"
        goalHours.toFloatOrNull()==null ->"Please enter hours"
        goalHours.toFloat()>1000f ->"Please enter hours"
        goalHours.toFloat()<1f ->"Please enter hours"
        else ->null
    }

    if(isOpen){
    AlertDialog(onDismissRequest = { /*TODO*/ },
        title = { Text(text = title) },
        text = {
            Column {
                Row(
                    modifier= Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    horizontalArrangement = Arrangement.SpaceAround
                ) {
                    Subject.subCardColor.forEach{colors ->
                        Box (
                            modifier = Modifier
                                .size(24.dp)
                                .clip(CircleShape)
                                .border(
                                    width = 1.dp,
                                    color = if (colors == selectedColor) Color.Black else Color.Transparent,
                                    shape = CircleShape
                                )
                                .background(brush = Brush.verticalGradient(colors))
                                .clickable { onColorChange(colors) }
                        )
                    }
                }
                OutlinedTextField(
                    value = subjectName,
                    onValueChange = onSubjectNameChange,
                    label ={ Text(text = "Subject Name")},
                    singleLine=true,
                    isError = suberror!=null && subjectName.isNotBlank(),
                    supportingText = { Text(text = suberror.orEmpty())}
                )
                Spacer(modifier = Modifier.height(10.dp))
                OutlinedTextField(
                    value = goalHours,
                    onValueChange = onGoalHourChange,
                    label ={ Text(text = "Goal Study Hours")},
                    singleLine=true,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    isError = hrerror!=null && goalHours.isNotBlank(),
                    supportingText = { Text(text = hrerror.orEmpty())}
                )
            }
        },
        confirmButton ={
            TextButton(onClick =onDismissReq,
                enabled = suberror==null && hrerror==null) {
                Text(text = "Save")
            }
        },
        dismissButton = {
            TextButton(onClick = onConfirmButtonClicked) {
                Text(text = "Cancel")
            }
        })
}

}