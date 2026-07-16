package com.example.classroom.navigation

sealed class Screen(val route: String) {
    data object Login : Screen("login")
    data object Register : Screen("register")
    data object Dashboard : Screen("dashboard")
    data object Announcement : Screen("announcement")
    data object Poll : Screen("poll")
    data object Subject : Screen("subject")
    data object Attendance : Screen("attendance/{subjectId}")
    data object Notes : Screen("notes")
    data object Exam : Screen("exam")
}

