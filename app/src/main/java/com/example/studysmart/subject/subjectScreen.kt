package com.example.studysmart.subject

import android.content.ClipData.Item
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LargeTopAppBar
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.example.studysmart.components.AddSubjectDialog
import com.example.studysmart.components.CountCard
import com.example.studysmart.components.DeleteDialog
import com.example.studysmart.components.StudySessionList
import com.example.studysmart.components.Tasklist
import com.example.studysmart.data.Subject
import com.example.studysmart.destinations.TaskScreenRouteDestination
import com.example.studysmart.sessions
import com.example.studysmart.task.TaskScreenNavArgs
import com.example.studysmart.tasks
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

data class SubjectScreenNavArgs(
    val subjectId:Int
)

@Destination(navArgsDelegate = SubjectScreenNavArgs::class)
@Composable
fun SubjectScreenRoute(
    navigator: DestinationsNavigator
){
    subjectScreen(
        onBackButtonClicked = {
                              navigator.navigateUp()
                              },
        onAddTaskClicked = {
                           val navArgs=TaskScreenNavArgs(null,-1)
            navigator.navigate(TaskScreenRouteDestination(navArgs))
                           },
        onTaskCardClicked = {taskId->
            val navArgs=TaskScreenNavArgs(taskId=taskId,subjectId = null)
            navigator.navigate(TaskScreenRouteDestination(navArgs))
        }
    )
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun subjectScreen(
    onBackButtonClicked: () -> Unit,
    onAddTaskClicked:()->Unit,
    onTaskCardClicked:(Int?)->Unit
){


    val scrollBehavior=TopAppBarDefaults.exitUntilCollapsedScrollBehavior()
    val listState= rememberLazyListState()
    val isExp by remember {
        derivedStateOf { listState.firstVisibleItemIndex ==0 }
    }

    var isDailogBoxOpen by rememberSaveable{ mutableStateOf(false) }

    var isDelDailogBoxOpen by rememberSaveable{ mutableStateOf(false) }
    var isDelSubDailogBoxOpen by rememberSaveable{ mutableStateOf(false) }

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
        isOpen = isDelSubDailogBoxOpen,
        title = "Delete Subject?",
        BodyText = "Are you sure you want to delete this subject",
        onDismissReq = {isDelSubDailogBoxOpen=false},
        onConfirmButtonClicked = {isDelSubDailogBoxOpen=false})

    DeleteDialog(
        isOpen = isDelDailogBoxOpen,
        title = "Delete Session?",
        BodyText = "Are you sure you want to delete this session",
        onDismissReq = {isDelDailogBoxOpen=false},
        onConfirmButtonClicked = {isDelDailogBoxOpen=false})

    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            SubjectScreenTopBar(
                title = "English",
                onBackButtonClicked = onBackButtonClicked,
                onDeleteButtonClicked = { isDelSubDailogBoxOpen=true },
                onEditButtonClicked = {isDailogBoxOpen=true},
                scrollBehavior=scrollBehavior
                )
        },
        floatingActionButton = {
            ExtendedFloatingActionButton(
                onClick = onAddTaskClicked,
                icon={ Icon(imageVector = Icons.Default.Add, contentDescription = "")},
                text = { Text(text = "ADD TASK")},
                expanded = isExp)
        }
    ) {
        LazyColumn(modifier = Modifier
            .fillMaxSize()
            .padding(it),
            state = listState
        )
        {
            item{
                subjectOverviewSection(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(12.dp) ,
                    studyHour = "10",
                    goalHour = "15",
                    progress = 0.75f)
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
            Tasklist(
                sectionTitle = "Completed Tasks",
                emptyListText = "You don't have any task.\n" +
                        " Click the + button to add task.",
                tasks = tasks,
                onCheckedClick = {},
                onTaskCardClick = {}
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
private fun SubjectScreenTopBar(
    title:String,
    onBackButtonClicked:()->Unit,
    onDeleteButtonClicked:()->Unit,
    onEditButtonClicked:()->Unit,
    scrollBehavior: TopAppBarScrollBehavior
){
    LargeTopAppBar(
        scrollBehavior = scrollBehavior,
        navigationIcon = {
            IconButton(onClick = onBackButtonClicked) {
                Icon(imageVector = Icons.Default.ArrowBack, contentDescription = " ")
            }
        },
        title = {
            Text(
                text = title,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                style=MaterialTheme.typography.headlineSmall
            )
        },
        actions = {
            IconButton(onClick = onDeleteButtonClicked) {
                Icon(imageVector = Icons.Default.Delete, contentDescription = "")
            }
            IconButton(onClick = onEditButtonClicked) {
                Icon(imageVector = Icons.Default.Edit, contentDescription = "")
            }
        }
    )
}


@Composable
fun subjectOverviewSection(
    modifier: Modifier,
    studyHour:String,
    goalHour:String,
    progress:Float
){
    var perPro = remember(progress) {
    (progress*100).toInt().coerceIn(0,100)
    }
    Row (
        horizontalArrangement = Arrangement.SpaceAround,
        verticalAlignment = Alignment.CenterVertically
    ){
        CountCard(
            modifier = Modifier.weight(1f),
            headingText = "Goal Study Hour",
            count =goalHour
        )
        Spacer(modifier = Modifier.width(10.dp))
        CountCard(
            modifier = Modifier.weight(1f),
            headingText = "Study Hour",
            count =studyHour
        )
        Spacer(modifier = Modifier.width(10.dp))
        Box (
            modifier = Modifier.size(75.dp),
            contentAlignment = Alignment.Center
        ){
            CircularProgressIndicator(
                modifier = Modifier.fillMaxSize(),
                progress = 1f,
                strokeWidth = 4.dp,
                strokeCap = StrokeCap.Round,
                color = MaterialTheme.colorScheme.surfaceVariant
            )
            CircularProgressIndicator(
                modifier = Modifier.fillMaxSize(),
                progress = progress,
                strokeWidth = 4.dp,
                strokeCap = StrokeCap.Round
            )
            Text(text = "$perPro %")
        }
    }
}