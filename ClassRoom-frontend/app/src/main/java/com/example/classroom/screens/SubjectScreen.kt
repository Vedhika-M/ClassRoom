package com.example.classroom.screens

import androidx.compose.foundation.clickable
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
import androidx.navigation.NavController
import com.example.classroom.utils.SessionManager
import com.example.classroom.viewmodel.MainViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SubjectScreen(
    navController: NavController
) {
    val viewModel: MainViewModel = viewModel()

    val subjects by viewModel.subjects.collectAsState()

    var showDialog by remember {
        mutableStateOf(false)
    }

    var subjectName by remember {
        mutableStateOf("")
    }

    var subjectCode by remember {
        mutableStateOf("")
    }

    LaunchedEffect(Unit) {
        viewModel.loadSubjects()
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text("Subjects")
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
            items(subjects) { subject ->
                Card(
                    modifier = Modifier.clickable {
                        navController.navigate("attendance/${subject.id}")
                    },
                    elevation = CardDefaults.cardElevation(4.dp)
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp)
                    ) {
                        Text(
                            text = subject.name,
                            style = MaterialTheme.typography.titleMedium
                        )

                        Spacer(
                            modifier = Modifier.height(8.dp)
                        )

                        Text(
                            text = subject.code
                        )
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
                Text("Create Subject")
            },
            text = {
                Column {
                    OutlinedTextField(
                        value = subjectName,
                        onValueChange = {
                            subjectName = it
                        },
                        label = {
                            Text("Subject Name")
                        }
                    )

                    Spacer(
                        modifier = Modifier.height(12.dp)
                    )

                    OutlinedTextField(
                        value = subjectCode,
                        onValueChange = {
                            subjectCode = it
                        },
                        label = {
                            Text("Subject Code")
                        }
                    )
                }
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        viewModel.createSubject(
                            subjectName,
                            subjectCode
                        )

                        subjectName = ""
                        subjectCode = ""

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