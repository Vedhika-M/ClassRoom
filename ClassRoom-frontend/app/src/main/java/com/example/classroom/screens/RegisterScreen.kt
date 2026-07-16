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
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.classroom.components.ClassroomButton
import com.example.classroom.components.ClassroomTextField
import com.example.classroom.components.PasswordTextField
import com.example.classroom.navigation.Screen
import com.example.classroom.viewmodel.AuthViewModel
import androidx.compose.material3.Checkbox
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Text

@Composable
fun RegisterScreen(
    navController: NavController
) {
    val context = LocalContext.current
    val authViewModel: AuthViewModel = viewModel()

    val loading by authViewModel.loading.collectAsState()
    val authResponse by authViewModel.authResponse.collectAsState()
    val error by authViewModel.error.collectAsState()

    var isCR by remember {
        mutableStateOf(false)
    }

    var crSecret by remember {
        mutableStateOf("")
    }

    var username by remember {
        mutableStateOf("")
    }

    var email by remember {
        mutableStateOf("")
    }

    var password by remember {
        mutableStateOf("")
    }

    LaunchedEffect(authResponse) {
        authResponse?.let {
            Toast.makeText(
                context,
                it.message,
                Toast.LENGTH_SHORT
            ).show()

            navController.navigate(Screen.Login.route) {
                popUpTo(Screen.Register.route) {
                    inclusive = true
                }
            }
            authViewModel.clearResponse()
        }
    }

    LaunchedEffect(error) {
        if (!error.isNullOrEmpty()) {
            Toast.makeText(
                context,
                error,
                Toast.LENGTH_LONG
            ).show()
            authViewModel.clearError()
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Create Account",
            style = MaterialTheme.typography.headlineLarge,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(32.dp))

        ClassroomTextField(
            value = username,
            onValueChange = {
                username = it
            },
            label = "Username"
        )

        Spacer(modifier = Modifier.height(16.dp))

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

        Spacer(modifier = Modifier.height(12.dp))

        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Checkbox(
                checked = isCR,
                onCheckedChange = {
                    isCR = it
                }
            )

            Text("Register as Class Representative")
        }

        if (isCR) {
            Spacer(modifier = Modifier.height(12.dp))

            PasswordTextField(
                password = crSecret,
                onPasswordChange = {
                    crSecret = it
                },
                label = "CR Secret"
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        ClassroomButton(
            text = "Register"
        ) {
            if (
                username.isBlank() ||
                email.isBlank() ||
                password.isBlank()
            ) {
                Toast.makeText(
                    context,
                    "Fill all fields",
                    Toast.LENGTH_SHORT
                ).show()
                return@ClassroomButton
            }

            if (isCR && crSecret.isBlank()) {
                Toast.makeText(
                    context,
                    "Enter CR Secret",
                    Toast.LENGTH_SHORT
                ).show()

                return@ClassroomButton
            }

            authViewModel.register(
                username,
                email,
                password,
                isCR,
                if (isCR) crSecret else null
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        if (loading) {
            CircularProgressIndicator()
            Spacer(modifier = Modifier.height(16.dp))
        }

        TextButton(
            onClick = {
                navController.popBackStack()
            }
        ) {
            Text(
                "Already have an account? Login"
            )
        }
    }
}
