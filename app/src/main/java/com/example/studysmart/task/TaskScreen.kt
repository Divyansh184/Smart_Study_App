package com.example.studysmart.task

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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.studysmart.components.DeleteDialog
import com.example.studysmart.components.SubjectListBottomSheet
import com.example.studysmart.components.TaskCheckBox
import com.example.studysmart.components.TaskDatePiker
import com.example.studysmart.subjects
import com.example.studysmart.util.Priority
import com.example.studysmart.util.changeMillisToDateString
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import kotlinx.coroutines.launch
import java.time.Instant

data class TaskScreenNavArgs(
    val taskId:Int?,
    val subjectId:Int?
)

@Destination(navArgsDelegate = TaskScreenNavArgs::class)
@Composable
fun TaskScreenRoute(
    navigator: DestinationsNavigator
){
    TaskScreen(
        onBackButtonClicked = {
            navigator.navigateUp()
        }
    )
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun TaskScreen(
    onBackButtonClicked: () -> Unit
){
    var title by remember {
        mutableStateOf("")
    }
    var description by remember {
        mutableStateOf("")
    }
    var taskTitleError by rememberSaveable {
        mutableStateOf<String?>(null)
    }

    var isDelDiaOpen by rememberSaveable {
        mutableStateOf(false)
    }

    var isDatePickerOpen by rememberSaveable {
        mutableStateOf(false)
    }

    var isBottomSheetOpen by rememberSaveable {
        mutableStateOf(false)
    }

    val scope= rememberCoroutineScope()

    val sheetState= rememberModalBottomSheetState()

    val datePickerState= rememberDatePickerState(
        initialSelectedDateMillis = Instant.now().toEpochMilli()
    )

    taskTitleError =when{
        title.isEmpty()->"Please enter the task title."
        title.length<4 ->"Please enter the task title."
        title.length>30 ->"Please enter the task title."
        else -> null
    }

    DeleteDialog(
        isOpen =isDelDiaOpen ,
        title = "Delete Task",
        BodyText = "Are you sure you want to delete this task",
        onDismissReq = {isDelDiaOpen=false },
        onConfirmButtonClicked = {isDelDiaOpen=false}
    )

    TaskDatePiker(
        state = datePickerState,
        isOpen = isDatePickerOpen,
        onDismissRequest = { isDatePickerOpen=false },
        onConfirmButtonCLicked = {isDatePickerOpen=false}
    )

    SubjectListBottomSheet(
        sheetState = sheetState,
        isOpen = isBottomSheetOpen,
        subjects = subjects,
        onSubjectClicked = {
                           scope.launch { sheetState.hide() }.invokeOnCompletion {
                               if(!sheetState.isVisible) isBottomSheetOpen=false
                           }
        },
        onDismissRequest ={isBottomSheetOpen=false}
    )

    Scaffold (
        topBar ={ TaskScreenTopBar(
            isTaskExist = true,
            isComplete = true,
            checkBoxBorderColor = Color.Red,
            onBackButtonClicked =onBackButtonClicked,
            onDeleteButtonClicked = { isDelDiaOpen=true },
            onCheckBoxClicked = {})
        }
    ){
        Column(modifier = Modifier
            .verticalScroll(state = rememberScrollState())
            .fillMaxWidth()
            .padding(it)
            .padding(horizontal = 12.dp)) {
            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                value =title ,
                onValueChange ={title=it},
                label = { Text(text = "Title")},
                singleLine = true,
                isError = taskTitleError!=null && title.isNotBlank(),
                supportingText = { Text(text = taskTitleError.orEmpty())}
            )
            Spacer(Modifier.height(10.dp))
            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                value =description ,
                onValueChange ={description=it},
                label = { Text(text = "Description")}
            )
            Spacer(Modifier.height(20.dp))
            Text(text = "Due Date", style = MaterialTheme.typography.bodySmall )

            Row (
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ){
                Text(text = datePickerState.selectedDateMillis.changeMillisToDateString(), style = MaterialTheme.typography.bodyLarge )
                IconButton(onClick = { isDatePickerOpen=true }) {
                    Icon(imageVector = Icons.Default.DateRange, contentDescription = "")
                }
            }
            Spacer(Modifier.height(10.dp))
            Text(text = "Priority", style = MaterialTheme.typography.bodySmall )
            Row (
                modifier = Modifier.fillMaxWidth()
            ){
                Priority.entries.forEach { p ->
                    PriorityButton(
                        modifier = Modifier.weight(1f),
                        label = p.tit,
                        bgColor =p.color,
                        borderColor = if(p==Priority.MEDIUM) Color.White
                        else Color.Transparent,
                        labelColor =if(p==Priority.MEDIUM) Color.White
                        else Color.White.copy(alpha = 0.7f),
                        onClick = {}
                    )
                }
            }
            Spacer(Modifier.height(30.dp))
            Text(text = "Related to Subject", style = MaterialTheme.typography.bodySmall )

            Row (
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ){
                Text(text = "English", style = MaterialTheme.typography.bodyLarge )
                IconButton(onClick = { isBottomSheetOpen=true }) {
                    Icon(imageVector = Icons.Default.ArrowDropDown, contentDescription = "")
                }
            }
            
            Button(
                enabled = taskTitleError==null,
                onClick = { /*TODO*/ },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 20.dp)
            ) {
                Text(text = "Save")
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun TaskScreenTopBar(
    isTaskExist:Boolean,
    isComplete:Boolean,
    checkBoxBorderColor:Color,
    onBackButtonClicked:()->Unit,
    onDeleteButtonClicked:()->Unit,
    onCheckBoxClicked:()->Unit
){
    TopAppBar(
        navigationIcon = {
                         IconButton(onClick = onBackButtonClicked) {
                             Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "")
                         }
        },
        title = { Text(text = "Task", style = MaterialTheme.typography.headlineSmall) },
        actions = {
            if(isTaskExist){
                TaskCheckBox(isComplete = isComplete, borderColor = checkBoxBorderColor, onCheckClicked = onCheckBoxClicked)
                IconButton(onClick = onDeleteButtonClicked) {
                    Icon(imageVector = Icons.Default.Delete, contentDescription = "")
                }
            }
        }
    )
}



@Composable
fun PriorityButton(
    modifier: Modifier=Modifier,
    label:String,
    bgColor:Color,
    borderColor:Color,
    labelColor: Color,
    onClick:()->Unit
){
    Box(modifier = modifier
        .background(bgColor)
        .clickable { onClick() }
        .padding(5.dp)
        .border(1.dp, borderColor, RoundedCornerShape(5.dp))
        .padding(5.dp),
        contentAlignment = Alignment.Center){
        Text(text = label, color = labelColor)
    }
}