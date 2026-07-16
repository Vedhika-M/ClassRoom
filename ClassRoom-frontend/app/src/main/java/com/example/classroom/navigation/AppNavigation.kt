package com.example.classroom.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.classroom.screens.*

@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Screen.Login.route
    ) {
        composable(Screen.Login.route) {
            LoginScreen(navController)
        }

        composable(Screen.Register.route) {
            RegisterScreen(navController)
        }

        composable(Screen.Dashboard.route) {
            DashboardScreen(navController)
        }

        composable(Screen.Announcement.route) {
            AnnouncementScreen()
        }

        composable(Screen.Poll.route) {
            PollScreen()
        }

        composable(Screen.Subject.route) {
            SubjectScreen(navController)
        }

        composable(
            "attendance/{subjectId}"
        ) { backStackEntry ->
            val subjectId =
                backStackEntry.arguments
                    ?.getString("subjectId")
                    ?.toInt() ?: 1
            AttendanceScreen(subjectId)
        }

        composable(Screen.Notes.route) {
            NotesScreen()
        }

        composable(Screen.Exam.route) {
            ExamScreen()
        }
    }
}