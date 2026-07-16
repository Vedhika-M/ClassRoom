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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
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
fun PollScreen() {
    val viewModel: MainViewModel = viewModel()

    val polls by viewModel.polls.collectAsState()

    var showDialog by remember {
        mutableStateOf(false)
    }

    var question by remember {
        mutableStateOf("")
    }

    var option1 by remember {
        mutableStateOf("")
    }

    var option2 by remember {
        mutableStateOf("")
    }

    var hasVoted by remember {
        mutableStateOf(false)
    }

    val votedPolls = remember {
        mutableStateListOf<Int>()
    }

    LaunchedEffect(Unit) {
        viewModel.loadPolls()
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text("Polls")
                }
            )
        },
       floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    showDialog = true
                }
            ) {
                Icon(Icons.Default.Add, null)
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
            items(polls) { poll ->
                Card(
                    elevation = CardDefaults.cardElevation(4.dp)
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp)
                    ) {
                        Text(
                            text = poll.question,
                            style = MaterialTheme.typography.titleMedium
                        )

                        Spacer(modifier = Modifier.height(12.dp))

                        if (poll.isClosed) {
                            Text(
                                text = "Poll Closed",
                                color = MaterialTheme.colorScheme.error
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                        }

                        if (!poll.hasVoted && !poll.isClosed) {
                            poll.options.forEach { option ->
                                Button(
                                    modifier = Modifier.fillMaxWidth(),
                                    onClick = {
                                        viewModel.votePoll(
                                            poll.id,
                                            option.id
                                        )
                                    }
                                ) {
                                    Text(option.option)
                                }
                                Spacer(modifier = Modifier.height(8.dp))
                            }
                        }else{
                            poll.options.forEach { option ->
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween
                                ) {
                                    Text(option.option)
                                    Text("${option.votes} votes")
                                }
                                Spacer(modifier = Modifier.height(8.dp))
                            }
                        }
                        Spacer(modifier = Modifier.height(8.dp))
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
                Text("Create Poll")
            },
            text = {
                Column {
                    OutlinedTextField(
                        value = question,
                        onValueChange = {
                            question = it
                        },
                        label = {
                            Text("Question")
                        }
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    OutlinedTextField(
                        value = option1,
                        onValueChange = {
                            option1 = it
                        },
                        label = {
                            Text("Option 1")
                        }
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    OutlinedTextField(
                        value = option2,
                        onValueChange = {
                            option2 = it
                        },
                        label = {
                            Text("Option 2")
                        }
                    )
                }
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        viewModel.createPoll(
                            question,
                            listOf(option1, option2)
                        )

                        question = ""
                        option1 = ""
                        option2 = ""

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