package com.example.classroom.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.classroom.viewmodel.MainViewModel
import androidx.compose.material3.Button
import androidx.compose.material3.RadioButton
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.TextButton
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.navigation.NavController
import com.example.classroom.model.AttendanceStudent
import com.example.classroom.model.MarkAttendanceRequest
import com.example.classroom.utils.SessionManager
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.util.Calendar
import java.text.SimpleDateFormat
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AttendanceScreen(
    subjectId: Int = 1
) {
    val viewModel: MainViewModel = viewModel()

    val attendanceHistory by viewModel.attendanceHistory.collectAsState()
    val attendanceSummary by viewModel.attendanceSummary.collectAsState()
    val students by viewModel.students.collectAsState()

    val calendar = Calendar.getInstance()
    val currentDate = "${calendar.get(Calendar.YEAR)}-" +
            "${calendar.get(Calendar.MONTH) + 1}-" +
            "${calendar.get(Calendar.DAY_OF_MONTH)}"


    val snackbarHostState = remember { SnackbarHostState() }

    val scope = rememberCoroutineScope()

    val attendanceMap = remember {
        mutableStateMapOf<Int, String>()
    }

    var showSummaryDialog by remember {
        mutableStateOf(false)
    }

    fun formatDate(date: String): String {
        return try {
            val input = SimpleDateFormat(
                "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'",
                Locale.getDefault()
            )

            val output = SimpleDateFormat(
                "dd",
                Locale.getDefault()
            )

            output.format(input.parse(date)!!)
        } catch (e: Exception) {
            "--"
        }
    }

    fun formatMonth(date: String): String {
        return try {
            val input = SimpleDateFormat(
                "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'",
                Locale.getDefault()
            )

            val output = SimpleDateFormat(
                "MMM",
                Locale.getDefault()
            )

            output.format(input.parse(date)!!)
        } catch (e: Exception) {
            "---"
        }
    }

    LaunchedEffect(Unit) {
        if (SessionManager.getRole() == "CR") {
            viewModel.loadStudents()
        } else {
            viewModel.loadAttendanceHistory(subjectId)
            viewModel.loadAttendanceSummary(subjectId)
        }
    }

    Scaffold(
        snackbarHost = {
            SnackbarHost(hostState = snackbarHostState)
        },
        topBar = {
            TopAppBar(
                title = {
                    Text("Attendance")
                }
            )
        }
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            if (SessionManager.getRole() == "CR") {
                items(students) { student ->
                    Card(
                        elevation = CardDefaults.cardElevation(4.dp)
                    ) {
                        Column(
                            modifier = Modifier.padding(16.dp)
                        ) {
                            Text(
                                student.username,
                                fontWeight = FontWeight.Bold
                            )

                            Spacer(modifier = Modifier.height(8.dp))

                            Row {
                                RadioButton(
                                    selected =
                                        attendanceMap[student.id] == "PRESENT",

                                    onClick = {
                                        attendanceMap[student.id] = "PRESENT"
                                    }
                                )

                                Text("Present")

                                Spacer(modifier = Modifier.width(16.dp))

                                RadioButton(
                                    selected =
                                        attendanceMap[student.id] == "ABSENT",
                                    onClick = {
                                        attendanceMap[student.id] = "ABSENT"
                                    }
                                )
                                Text("Absent")
                            }
                        }
                    }
                }

                item {
                    Button(
                        modifier = Modifier.fillMaxWidth(),
                        onClick = {
                            val request =
                                MarkAttendanceRequest(
                                    date = currentDate,
                                    attendance =
                                        attendanceMap.map {
                                            AttendanceStudent(
                                                studentId = it.key,
                                                status = it.value
                                            )
                                        }
                                )
                            viewModel.markAttendance(
                                subjectId,
                                request
                            )
                            scope.launch {
                                snackbarHostState.showSnackbar(
                                    message = "Attendance saved successfully"
                                )
                            }
                        }
                    ) {
                        Text("Save Attendance")
                    }

                    Button(
                        modifier = Modifier.fillMaxWidth(),
                        onClick = {
                            showSummaryDialog = true
                        }
                    ) {
                        Text("View your Attendance Summary")
                    }
                }
            } else {
                item {
                    Card(
                        elevation = CardDefaults.cardElevation(4.dp)
                    ) {
                        Column(
                            modifier = Modifier.padding(16.dp)
                        ) {
                            Text(
                                "Attendance Summary",
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.Bold
                            )

                            Spacer(
                                modifier = Modifier.height(8.dp)
                            )

                            attendanceSummary?.let {
                                Text("Present : ${it.totalPresent}")
                                Text("Absent : ${it.totalAbsent}")
                                Text("Percentage : ${it.attendancePercentage}%")
                            }
                        }
                    }
                }

                items(attendanceHistory) { attendance ->
                    Card(
                        elevation = CardDefaults.cardElevation(4.dp)
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column(
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Text(
                                    text = formatDate(attendance.attendanceSession.date),
                                    style = MaterialTheme.typography.headlineSmall
                                )

                                Text(
                                    text = formatMonth(attendance.attendanceSession.date),
                                    style = MaterialTheme.typography.bodySmall
                                )
                            }

                            Spacer(modifier = Modifier.width(20.dp))

                            Column {
                                Text(
                                    text = when(attendance.status) {
                                        "PRESENT" -> "Present"
                                        "ABSENT" -> "Absent"
                                        else -> "No Class"
                                    }
                                )
                            }
                        }
                    }
                }
            }
        }
    }
    if (showSummaryDialog) {
        AlertDialog(
            onDismissRequest = {
                showSummaryDialog = false
            },
            title = {
                Text("Attendance Summary")
            },
            text = {
                if (attendanceSummary != null) {
                    Column {
                        Text("Present : ${attendanceSummary!!.totalPresent}")
                        Text("Absent : ${attendanceSummary!!.totalAbsent}")
                        Text(
                            "Percentage : ${attendanceSummary!!.attendancePercentage}%"
                        )
                    }
                } else {
                    Text("No attendance available.")
                }
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        showSummaryDialog = false
                    }
                ) {
                    Text("Close")
                }
            }
        )
    }
}