package com.example.studysmart.session

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.studysmart.components.DeleteDialog
import com.example.studysmart.components.StudySessionList
import com.example.studysmart.components.SubjectListBottomSheet
import com.example.studysmart.sessions
import com.example.studysmart.subjects
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import kotlinx.coroutines.launch

@Destination
@Composable
fun SessionScreenRoute(
    navigator: DestinationsNavigator
){
    SessionScreen(
        onBackButtonClicked = {
            navigator.navigateUp()
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun SessionScreen(
    onBackButtonClicked: () -> Unit
){

    var isBottomSheetOpen by rememberSaveable {
        mutableStateOf(false)
    }

    val scope= rememberCoroutineScope()

    val sheetState= rememberModalBottomSheetState()

    var isDelDiaOpen by rememberSaveable {
        mutableStateOf(false)
    }


    DeleteDialog(
        isOpen =isDelDiaOpen ,
        title = "Delete Session?",
        BodyText = "Are you sure you want to delete this session.",
        onDismissReq = {isDelDiaOpen=false },
        onConfirmButtonClicked = {isDelDiaOpen=false}
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

    Scaffold(
        topBar = { SessionScreenTopBar(onBackButtonClicked = onBackButtonClicked)}
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
        ){
            item {
                TimerSection(modifier = Modifier
                    .fillMaxSize()
                    .aspectRatio(1f)
                )
            }
            item {
                RelatedToSubjectScreen(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(12.dp),
                    relatedtosub = "English",
                    onSubjectButtonClicked = {
                        isBottomSheetOpen=true
                    })
            }
            item { 
                ButtonSection(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(12.dp),
                    startButClick = {},
                    cancelButClick = {},
                    finishButClick = {}
                )
            }
            StudySessionList(
                sectionTitle = "Study Session History",
                emptyListText = "You don't have any session.\n" +
                        " Start a new Study Session to record process.",
                session = sessions,
                onDeleteIconClicked = {isDelDiaOpen=true}
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SessionScreenTopBar(
    onBackButtonClicked:()->Unit
){
    TopAppBar(
        navigationIcon ={
                        IconButton(onClick =  onBackButtonClicked ) {
                            Icon(imageVector = Icons.Default.ArrowBack, contentDescription = " ")
                        }
        } ,
        title = { Text(text = "Study Session", style = MaterialTheme.typography.headlineSmall) }
    )
}

@Composable
fun TimerSection(
    modifier: Modifier
){
    Box(modifier = modifier, contentAlignment = Alignment.Center){
        Box(modifier = Modifier
            .size(250.dp)
            .border(5.dp, MaterialTheme.colorScheme.surfaceVariant, CircleShape)
        )
        Text(
            text = "00:05:13",
            style = MaterialTheme.typography.titleLarge.copy(fontSize = 45.sp)
        )
    }
}

@Composable
fun RelatedToSubjectScreen(
    modifier: Modifier,
    relatedtosub:String,
    onSubjectButtonClicked:()->Unit
){
    Column( modifier = modifier) {
        Text(text = "Related to Subject", style = MaterialTheme.typography.bodySmall )

        Row (
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ){
            Text(text =relatedtosub, style = MaterialTheme.typography.bodyLarge )
            IconButton(onClick = onSubjectButtonClicked) {
                Icon(imageVector = Icons.Default.ArrowDropDown, contentDescription = "")
            }
        }
    }

}

@Composable
fun ButtonSection(
    modifier: Modifier,
    startButClick:()->Unit,
    cancelButClick:()->Unit,
    finishButClick:()->Unit
){
    Row (
        modifier=modifier,
        horizontalArrangement = Arrangement.SpaceBetween
    ){
        Button(onClick = cancelButClick) {
            Text(
                modifier=Modifier.padding(horizontal = 10.dp, vertical = 5.dp),
                text="Cancel"
            )
        }
        Button(onClick = startButClick) {
            Text(
                modifier=Modifier.padding(horizontal = 10.dp, vertical = 5.dp),
                text="Start"
            )
        }
        Button(onClick = finishButClick ) {
            Text(
                modifier=Modifier.padding(horizontal = 10.dp, vertical = 5.dp),
                text="Finish"
            )
        }
    }
}