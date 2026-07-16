package com.example.classroom.model

data class Exam(
    val id: Int,
    val examType: String,
    val examDate: String,
    val examTime: String,
    val notes: String?,
    val subjectId: Int,
    val subject: Subject
)

data class CreateExamRequest(
    val examType:String,
    val examDate:String,
    val examTime:String,
    val notes:String?
)