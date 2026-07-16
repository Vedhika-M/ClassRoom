package com.example.classroom.model

data class Note(
    val id: Int,
    val title: String,
    val fileName: String,
    val filePath: String,
    val uploadedAt: String
)