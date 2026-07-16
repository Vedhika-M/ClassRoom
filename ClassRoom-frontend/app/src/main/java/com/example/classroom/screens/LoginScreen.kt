package com.example.classroom.screens

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.classroom.components.ClassroomButton
import com.example.classroom.components.ClassroomTextField
import com.example.classroom.components.PasswordTextField
import com.example.classroom.navigation.Screen
import com.example.classroom.utils.SessionManager
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.classroom.viewmodel.AuthViewModel

@Composable
fun LoginScreen(
    navController: NavController
) {
    val context = LocalContext.current

    val authViewModel: AuthViewModel = viewModel()

    val loading by authViewModel.loading.collectAsState()
    val authResponse by authViewModel.authResponse.collectAsState()
    val error by authViewModel.error.collectAsState()

    var email by remember {
        mutableStateOf("")
    }

    var password by remember {
        mutableStateOf("")
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Text(
            text = "ClassRoom",
            style = MaterialTheme.typography.headlineLarge,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "Welcome Back"
        )

        Spacer(modifier = Modifier.height(32.dp))

        ClassroomTextField(
            value = email,
            onValueChange = {
                email = it
            },
            label = "Email"
        )

        Spacer(modifier = Modifier.height(16.dp))

        PasswordTextField(
            password = password,
            onPasswordChange = {
                password = it
            }
        )

        Spacer(modifier = Modifier.height(24.dp))

        ClassroomButton(
            text = "Login"
        ) {
            if (email.isBlank() || password.isBlank()) {
                Toast.makeText(
                    context,
                    "Fill all fields",
                    Toast.LENGTH_SHORT
                ).show()

                return@ClassroomButton
            }
            authViewModel.login(
                email,
                password
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        if (loading) {
            CircularProgressIndicator()
            Spacer(modifier = Modifier.height(16.dp))
        }

        TextButton(
            onClick = {
                navController.navigate(
                    Screen.Register.route
                )
            }
        ) {
            Text(
                text = "Don't have an account? Register"
            )
        }
    }

    LaunchedEffect(authResponse) {
        authResponse?.let { response ->
            Toast.makeText(
                context,
                response.message,
                Toast.LENGTH_SHORT
            ).show()

            navController.navigate(
                Screen.Dashboard.route
            ) {
                popUpTo(Screen.Login.route) {
                    inclusive = true
                }
            }
            authViewModel.clearResponse()
        }
    }

    LaunchedEffect(error) {
        error?.let {
            Toast.makeText(
                context,
                it,
                Toast.LENGTH_LONG
            ).show()
            authViewModel.clearError()
        }
    }
}