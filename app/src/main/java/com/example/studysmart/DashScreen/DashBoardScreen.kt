package com.example.studysmart.DashScreen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ModifierLocalBeyondBoundsLayout
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.studysmart.R
import com.example.studysmart.components.AddSubjectDialog
import com.example.studysmart.components.CountCard
import com.example.studysmart.components.DeleteDialog
import com.example.studysmart.components.StudySessionList
import com.example.studysmart.components.SubjectCard
import com.example.studysmart.components.Tasklist
import com.example.studysmart.data.Session
import com.example.studysmart.data.Subject
import com.example.studysmart.data.Task
import com.example.studysmart.destinations.SessionScreenRouteDestination
import com.example.studysmart.destinations.SubjectScreenRouteDestination
import com.example.studysmart.destinations.TaskScreenRouteDestination
import com.example.studysmart.sessions
import com.example.studysmart.subject.SubjectScreenNavArgs
import com.example.studysmart.subjects
import com.example.studysmart.task.TaskScreenNavArgs
import com.example.studysmart.tasks
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@Destination(start=true)
@Composable
fun DashBoardScreenRoute(
    navigator: DestinationsNavigator
){
    DashBoardScreen(onSubjectCardClicked ={subid->
        subid?.let {
            val navArg=SubjectScreenNavArgs(subjectId = subid)
            navigator.navigate(SubjectScreenRouteDestination(navArg))
        }
    } ,
        onTaskCardClicked ={taskId->
                           val navArgs=TaskScreenNavArgs(taskId=taskId,subjectId = null)
                            navigator.navigate(TaskScreenRouteDestination(navArgs))
        },
        onSessionButtonClicked ={
            navigator.navigate(SessionScreenRouteDestination())
        }
    )
}


@Composable
private fun DashBoardScreen(
    onSubjectCardClicked:(Int)->Unit,
    onTaskCardClicked:(Int?)->Unit,
    onSessionButtonClicked:()->Unit
) {
//    val subjects = listOf(
//        Subject(name = "English", goalHr = 10f, color = Subject.subCardColor[0], subId = 0),
//        Subject(name = "Hindi", goalHr = 10f, color = Subject.subCardColor[1],subId = 0),
//        Subject(name = "Maths", goalHr = 10f, color = Subject.subCardColor[2],subId = 0),
//        Subject(name = "Computers", goalHr = 10f, color = Subject.subCardColor[3],subId = 0),
//        Subject(name = "AI", goalHr = 10f, color = Subject.subCardColor[4],subId = 0),
//    )
//
//
//    val tasks= listOf(
//        Task(title = "Go Study",desc="", dueDate = 0L, priority = 1, relToSub = "",isComplete = false, taskSubID = 0, taskID = 1),
//        Task(title = "Go Coaching",desc="", dueDate = 0L, priority = 0, relToSub = "",isComplete = true, taskSubID = 0, taskID = 1),
//        Task(title = "Do Homework",desc="", dueDate = 0L, priority = 2, relToSub = "",isComplete = false, taskSubID = 0, taskID = 1)
//    )
//
//    val sessions= listOf(
//        Session(
//            relSub = "English",
//            date= 0L,
//            duration = 0L,
//            sessionSubID = 2,
//            sessionId = 0
//        ),
//        Session(
//            relSub = "Physics",
//            date= 0L,
//            duration = 0L,
//            sessionSubID = 2,
//            sessionId = 0
//        ),
//        Session(
//            relSub = "Maths",
//            date= 0L,
//            duration = 0L,
//            sessionSubID = 2,
//            sessionId = 0
//        )
//    )
    var isDailogBoxOpen by rememberSaveable{ mutableStateOf(false) }

    var isDelDailogBoxOpen by rememberSaveable{ mutableStateOf(false) }

    var subName by rememberSaveable { mutableStateOf("") }
    var goalHr by rememberSaveable { mutableStateOf("") }
    var selcol by rememberSaveable { mutableStateOf(Subject.subCardColor.random()) }

    AddSubjectDialog(
        isOpen = isDailogBoxOpen,
        subjectName = subName,
        goalHours = goalHr,
        onGoalHourChange = {goalHr=it},
        onSubjectNameChange = {subName=it},
        selectedColor = selcol,
        onColorChange = {selcol=it},
        onDismissReq = { isDailogBoxOpen=false },
        onConfirmButtonClicked = {isDailogBoxOpen=false}
    )
    DeleteDialog(
        isOpen = isDelDailogBoxOpen,
        title = "Delete Session",
        BodyText = "Are you sure you want to delete this session",
        onDismissReq = {isDelDailogBoxOpen=false},
        onConfirmButtonClicked = {isDelDailogBoxOpen=false})
    Scaffold(
        topBar = { DashBoardScreenTopBar() }
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(it) // Apply padding directly here
        ) {
            item {
                CountCardSec(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(12.dp),
                    subCnt = 5,
                    stuHr = "15",
                    goalHr = "10"
                )
            }
            item {
                SubCardSec(
                    modifier = Modifier.fillMaxWidth(),
                    subjectList = subjects,
                    onItemButtonClick = {isDailogBoxOpen=true},
                    onSubjectCardClicked = onSubjectCardClicked)
            }
            item {
                Button(
                    onClick = onSessionButtonClicked,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 48.dp, vertical = 20.dp)
                ) {
                    Text(text = "Start Study Session")
                }
            }
            Tasklist(
                sectionTitle = "Upcoming Tasks",
                emptyListText = "You don't have any task.\n" +
                        " Click the + button to add task.",
                tasks = tasks,
                onCheckedClick = {},
                onTaskCardClick = onTaskCardClicked
            )
            item{
                Spacer(modifier = Modifier.height(20.dp))
            }
            StudySessionList(
                sectionTitle = "Recent Study Session",
                emptyListText = "You don't have any session.\n" +
                        " Start a new Study Session to record process.",
                session = sessions,
                onDeleteIconClicked = {isDelDailogBoxOpen=true}
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DashBoardScreenTopBar() {
    CenterAlignedTopAppBar(title = {
        Text(
            text = "StudySmart",
            style = MaterialTheme.typography.headlineMedium
        )
    })
}

@Composable
fun CountCardSec(
    modifier: Modifier,
    subCnt: Int,
    stuHr: String,
    goalHr: String
) {
    Row(modifier = modifier) {
        CountCard(modifier = Modifier.weight(1f), headingText = "Subject Count", count = "$subCnt")
        CountCard(modifier = Modifier.weight(1f), headingText = "Studied hour", count = stuHr)
        CountCard(modifier = Modifier.weight(1f), headingText = "Goal Study Hours", count = goalHr)
    }
}

@Composable
fun SubCardSec(
    modifier: Modifier,
    subjectList: List<Subject>,
    emptyListText: String = "You don't have any subject.\n Click the + button to add Subject.",
    onItemButtonClick:()->Unit,
    onSubjectCardClicked:(Int)->Unit
) {
    Column(modifier = modifier, horizontalAlignment = Alignment.CenterHorizontally) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "Subjects",
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier.padding(start = 12.dp)
            )
            IconButton(onClick = onItemButtonClick) {
                Icon(imageVector = Icons.Default.Add, contentDescription = "Add Subject")
            }

        }
        if (subjectList.isEmpty()) {
            Image(
                modifier = Modifier
                    .size(120.dp)
                    .align(Alignment.CenterHorizontally),
                painter = painterResource(id = R.drawable.img_book),
                contentDescription = ""
            )
            Text(
                text = emptyListText,
                style = MaterialTheme.typography.bodySmall,
                color = Color.Gray,
                textAlign = TextAlign.Center
            )
        }
        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            contentPadding = PaddingValues(start = 12.dp, end = 12.dp)
        ) {
            items(subjectList) {
                SubjectCard(subjectName = it.name, gradientColor = it.color,
                    onClick = {onSubjectCardClicked(it.subId)})
            }
        }
    }
}
