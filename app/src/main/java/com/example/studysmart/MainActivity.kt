package com.example.studysmart

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
//import com.example.studysmart.DashScreen.DashBoardScreen
import com.example.studysmart.data.Session
import com.example.studysmart.data.Subject
import com.example.studysmart.data.Task
//import com.example.studysmart.session.SessionScreen
//import com.example.studysmart.subject.subjectScreen
//import com.example.studysmart.task.TaskScreen
import com.example.studysmart.ui.theme.StudySmartTheme
import com.ramcosta.composedestinations.DestinationsNavHost

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            StudySmartTheme {
                DestinationsNavHost(navGraph = NavGraphs.root)
            }
        }
    }
}


val subjects = listOf(
    Subject(name = "English", goalHr = 10f, color = Subject.subCardColor[0], subId = 0),
    Subject(name = "Hindi", goalHr = 10f, color = Subject.subCardColor[1],subId = 0),
    Subject(name = "Maths", goalHr = 10f, color = Subject.subCardColor[2],subId = 0),
    Subject(name = "Computers", goalHr = 10f, color = Subject.subCardColor[3],subId = 0),
    Subject(name = "AI", goalHr = 10f, color = Subject.subCardColor[4],subId = 0),
)


val tasks= listOf(
    Task(title = "Go Study",desc="", dueDate = 0L, priority = 1, relToSub = "",isComplete = false, taskSubID = 0, taskID = 1),
    Task(title = "Go Coaching",desc="", dueDate = 0L, priority = 0, relToSub = "",isComplete = true, taskSubID = 0, taskID = 1),
    Task(title = "Do Homework",desc="", dueDate = 0L, priority = 2, relToSub = "",isComplete = false, taskSubID = 0, taskID = 1)
)

val sessions= listOf(
    Session(
        relSub = "English",
        date= 0L,
        duration = 0L,
        sessionSubID = 2,
        sessionId = 0
    ),
    Session(
        relSub = "Physics",
        date= 0L,
        duration = 0L,
        sessionSubID = 2,
        sessionId = 0
    ),
    Session(
        relSub = "Maths",
        date= 0L,
        duration = 0L,
        sessionSubID = 2,
        sessionId = 0
    )
)