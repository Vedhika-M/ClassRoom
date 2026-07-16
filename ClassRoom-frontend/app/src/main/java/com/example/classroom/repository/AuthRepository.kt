package com.example.classroom.repository

import com.example.classroom.api.RetrofitInstance
import com.example.classroom.model.LoginRequest
import com.example.classroom.model.SignupRequest

class AuthRepository {
    private val api = RetrofitInstance.api

    suspend fun login(
        email: String,
        password: String
    ) = api.login(
        LoginRequest(
            email = email,
            password = password
        )
    )

    suspend fun register(
        username: String,
        email: String,
        password: String,
        isCR: Boolean,
        crSecret: String?
    ) = api.register(
        SignupRequest(
            username = username,
            email = email,
            password = password,
            isCR = isCR,
            crSecret = crSecret
        )
    )
}