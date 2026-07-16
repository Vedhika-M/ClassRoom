package com.example.classroom.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.classroom.utils.SessionManager
import com.example.classroom.viewmodel.MainViewModel
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import androidx.compose.ui.graphics.Color
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExamScreen(
    subjectId: Int = 1
) {
    val viewModel: MainViewModel = viewModel()

    val exams by viewModel.exams.collectAsState()

    var showDialog by remember {
        mutableStateOf(false)
    }

    var subject by remember {
        mutableStateOf("")
    }

    var examType by remember {
        mutableStateOf("")
    }

    var examDate by remember {
        mutableStateOf("")
    }

    var examTime by remember {
        mutableStateOf("")
    }

    var notes by remember {
        mutableStateOf("")
    }

    fun formatDate(date: String): String {
        return try {
            val inputFormat = SimpleDateFormat(
                "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'",
                Locale.getDefault()
            )
            val outputFormat = SimpleDateFormat(
                "dd MMM yyyy",
                Locale.getDefault()
            )
            val parsedDate: Date = inputFormat.parse(date)!!
            outputFormat.format(parsedDate)
        } catch (e: Exception) {
            date
        }
    }

    LaunchedEffect(Unit) {
        viewModel.loadExams()
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text("Exams")
                }
            )
        },
        floatingActionButton = {
            if (SessionManager.getRole() == "CR") {
                FloatingActionButton(
                    onClick = {
                        showDialog = true
                    }
                ) {
                    Icon(
                        Icons.Default.Add,
                        contentDescription = null
                    )
                }
            }
        }
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(exams) { exam ->
                val isPastExam = try {
                    val inputFormat = SimpleDateFormat(
                        "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'",
                        Locale.getDefault()
                    )
                    val examDate = inputFormat.parse(exam.examDate)

                    examDate != null && examDate.before(Date())
                } catch (e: Exception) {
                    false
                }

                Card(
                    colors = CardDefaults.cardColors(
                        containerColor =
                            if (isPastExam)
                                Color(0xFFE0E0E0)
                            else
                                MaterialTheme.colorScheme.surface
                    ),
                    elevation = CardDefaults.cardElevation(4.dp)
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp)
                    ) {
                        Text(
                            text = exam.subject.name,
                            style = MaterialTheme.typography.titleMedium
                        )

                        Spacer(modifier = Modifier.height(4.dp))

                        Text(
                            text = exam.examType,
                            style = MaterialTheme.typography.bodyLarge
                        )

                        Spacer(
                            modifier = Modifier.height(8.dp)
                        )

                        Text(text = "Date : ${formatDate(exam.examDate)}")

                        Text("Time : ${exam.examTime}")

                        exam.notes?.let {
                            Text(it)
                        }

                        Spacer(modifier = Modifier.height(12.dp))

                        if (SessionManager.getRole() == "CR") {
                            TextButton(
                                onClick = {
                                    viewModel.deleteExam(exam.id)
                                }
                            ) {
                                Text("Delete Exam")
                            }
                        }
                    }
                }
            }
        }
    }

    if (showDialog) {
        AlertDialog(
            onDismissRequest = {
                showDialog = false
            },
            title = {
                Text("Create Exam")
            },
            text = {
                Column {
                    OutlinedTextField(
                        value = subject,
                        onValueChange = {
                            subject = it
                        },
                        label = {
                            Text("Subject")
                        }
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    OutlinedTextField(
                        value = examType,
                        onValueChange = {
                            examType = it
                        },
                        label = {
                            Text("Exam Type")
                        }
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    OutlinedTextField(
                        value = examDate,
                        onValueChange = {
                            examDate = it
                        },
                        label = {
                            Text("Exam Date (yyyy-mm-dd)")
                        }
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    OutlinedTextField(
                        value = examTime,
                        onValueChange = {
                            examTime = it
                        },
                        label = {
                            Text("Exam Time")
                        }
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    OutlinedTextField(
                        value = notes,
                        onValueChange = {
                            notes = it
                        },
                        label = {
                            Text("Notes (Optional)")
                        }
                    )
                }
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        viewModel.createExam(
                            subjectId,
                            examType,
                            examDate,
                            examTime,
                            if (notes.isBlank()) null else notes
                        )

                        examType = ""
                        examDate = ""
                        examTime = ""
                        notes = ""

                        showDialog = false
                    }
                ) {
                    Text("Create")
                }
            },
            dismissButton = {
                TextButton(
                    onClick = {
                        showDialog = false
                    }
                ) {
                    Text("Cancel")
                }
            }
        )
    }
}