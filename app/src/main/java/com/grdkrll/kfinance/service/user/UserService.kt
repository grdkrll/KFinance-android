package com.grdkrll.kfinance.service.user

import io.ktor.client.statement.*

interface UserService {
    suspend fun loginUser(email: String, password: String): HttpResponse
    suspend fun registerUser(email: String, password: String): HttpResponse
    suspend fun loginWithGoogle(googleIdToken: String): HttpResponse
    suspend fun changeUserData(handle: String, email: String, password: String, confirmPassword: String, token: String?): HttpResponse
}