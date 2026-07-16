package com.example.classroom.model

data class SignupRequest(
    val username: String,
    val email: String,
    val password: String,
    val isCR: Boolean,
    val crSecret: String?
)

data class LoginRequest(
    val email: String,
    val password: String
)

data class User(
    val id: Int,
    val username: String,
    val email: String = "",
    val role: String
)

data class AuthResponse(
    val message: String,
    val token: String,
    val user: User
)