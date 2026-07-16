package com.example.classroom.model

data class Subject(
    val id: Int,
    val name: String,
    val code: String,
    val createdAt: String
)

data class CreateSubjectRequest(
    val name: String,
    val code: String
)