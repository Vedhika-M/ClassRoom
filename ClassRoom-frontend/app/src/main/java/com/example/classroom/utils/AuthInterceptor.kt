package com.example.classroom.utils

import okhttp3.Interceptor
import okhttp3.Response

class AuthInterceptor : Interceptor {
    override fun intercept(
        chain: Interceptor.Chain
    ): Response {
        val token = SessionManager.getToken()

        val request = chain.request()
            .newBuilder()

        if (!token.isNullOrEmpty()) {
            request.addHeader(
                "Authorization",
                "Bearer $token"
            )
        }
        return chain.proceed(
            request.build()
        )
    }
}