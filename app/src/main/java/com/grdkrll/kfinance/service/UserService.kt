package com.grdkrll.kfinance.service

import io.ktor.client.statement.*

interface UserService {
    suspend fun loginUser(email: String, password: String): HttpResponse
    suspend fun registerUser(
        name: String,
        email: String,
        handle: String,
        password: String
    ): HttpResponse

    suspend fun loginWithGoogle(googleIdToken: String): HttpResponse
    suspend fun changeUserData(
        name: String,
        handle: String,
        email: String,
        password: String,
        confirmPassword: String,
        token: String?
    ): HttpResponse
}