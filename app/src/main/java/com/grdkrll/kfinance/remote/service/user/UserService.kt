package com.grdkrll.kfinance.remote.service.user

import io.ktor.client.statement.*

interface UserService {
    suspend fun loginUser(email: String, password: String): HttpResponse

    suspend fun registerUser(email: String, password: String): HttpResponse

    suspend fun loginWithGoogle(googleIdToken: String): HttpResponse
}