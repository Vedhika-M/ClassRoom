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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.example.classroom.utils.SessionManager
import android.net.Uri
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.TextButton
import android.content.Context
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.ui.platform.LocalContext
import java.io.File
import java.io.FileOutputStream

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NotesScreen(
    subjectId: Int = 1
) {
    val viewModel: MainViewModel = viewModel()
    val context = LocalContext.current
    val notes by viewModel.notes.collectAsState()

    var showUploadDialog by remember {
        mutableStateOf(false)
    }

    var noteTitle by remember {
        mutableStateOf("")
    }

    var selectedFileUri by remember {
        mutableStateOf<Uri?>(null)
    }

    val pdfPicker =
        rememberLauncherForActivityResult(
            contract = ActivityResultContracts.GetContent()
        ) { uri ->
            selectedFileUri = uri
        }

    LaunchedEffect(Unit) {
        viewModel.loadNotes(subjectId)
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text("Study Material")
                }
            )
        },
        floatingActionButton = {
            if (SessionManager.getRole() == "CR") {
                FloatingActionButton(
                    onClick = {
                        showUploadDialog = true
                    }
                ) {
                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = "Upload Material"
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
            items(notes) { note ->
                Card(
                    elevation = CardDefaults.cardElevation(4.dp)
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp)
                    ) {
                        Text(
                            text = note.title,
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold
                        )

                        Spacer(
                            modifier = Modifier.height(8.dp)
                        )

                        Text(
                            text = note.fileName,
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold
                        )

                        Spacer(
                            modifier = Modifier.height(8.dp)
                        )

                        Text("File : ${note.filePath}")

                        Spacer(
                            modifier = Modifier.height(8.dp)
                        )

                        Text(
                            "Uploaded : ${note.uploadedAt}"
                        )
                    }
                }
            }
        }
    }
    if (showUploadDialog) {
        AlertDialog(
            onDismissRequest = {
                showUploadDialog = false
            },
            title = {
                Text("Upload Note")
            },
            text = {
                Column {
                    OutlinedTextField(
                        value = noteTitle,
                        onValueChange = {
                            noteTitle = it
                        },
                        label = {
                            Text("Title")
                        }
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    Button(
                        onClick = {
                            pdfPicker.launch(
                                "application/pdf"
                            )

                        }
                    ) {
                        Text("Choose PDF")
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    Text(
                        text =
                            selectedFileUri?.lastPathSegment
                                ?: "No file selected"
                    )
                }
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        if (
                            noteTitle.isNotBlank() &&
                            selectedFileUri != null
                        ) {
                            val file = copyUriToFile(
                                context,
                                selectedFileUri!!
                            )

                            viewModel.uploadNote(
                                subjectId,
                                noteTitle,
                                file
                            )

                            noteTitle = ""
                            selectedFileUri = null
                            showUploadDialog = false
                        }
                    }
                ) {
                    Text("Upload")
                }
            },
            dismissButton = {
                TextButton(
                    onClick = {
                        showUploadDialog = false
                    }
                ) {
                    Text("Cancel")
                }
            }
        )
    }
}

private fun copyUriToFile(
    context: Context,
    uri: Uri
): File {
    val inputStream = context.contentResolver.openInputStream(uri)

    val file = File(
        context.cacheDir,
        "upload_${System.currentTimeMillis()}.pdf"
    )

    val outputStream = FileOutputStream(file)

    inputStream?.copyTo(outputStream)
    inputStream?.close()
    outputStream.close()

    return file
}