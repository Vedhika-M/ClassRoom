package com.example.classroom.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.classroom.model.AuthResponse
import com.example.classroom.repository.AuthRepository
import com.example.classroom.utils.SessionManager
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class AuthViewModel : ViewModel() {
    private val repository = AuthRepository()

    private val _loading = MutableStateFlow(false)
    val loading: StateFlow<Boolean> = _loading

    private val _authResponse = MutableStateFlow<AuthResponse?>(null)
    val authResponse: StateFlow<AuthResponse?> = _authResponse

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error

    fun login(
        email: String,
        password: String
    ) {
        viewModelScope.launch {
            _loading.value = true
            _error.value = null

            try {
                val response = repository.login(
                    email,
                    password
                )

                SessionManager.saveToken(response.token)
                SessionManager.saveRole(response.user.role)
                SessionManager.saveUsername(response.user.username)
                SessionManager.saveEmail(response.user.email)

                _authResponse.value = response
            } catch (e: Exception) {
                _error.value = e.message ?: "Login failed"
            }

            _loading.value = false
        }
    }

    fun register(
        username: String,
        email: String,
        password: String,
        isCR: Boolean,
        crSecret: String?
    ) {
        viewModelScope.launch {
            _loading.value = true
            _error.value = null
            try {
                val response = repository.register(
                    username,
                    email,
                    password,
                    isCR,
                    crSecret
                )

                SessionManager.saveToken(response.token)
                SessionManager.saveRole(response.user.role)
                SessionManager.saveUsername(response.user.username)
                SessionManager.saveEmail(response.user.email)

                _authResponse.value = response
            } catch (e: Exception) {
                _error.value = e.message ?: "Registration failed"
            }

            _loading.value = false
        }
    }

    fun clearResponse() {
        _authResponse.value = null
    }

    fun clearError() {
        _error.value = null
    }
}