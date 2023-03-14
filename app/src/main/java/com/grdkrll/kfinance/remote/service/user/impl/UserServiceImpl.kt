package com.grdkrll.kfinance.remote.service.user.impl

import com.grdkrll.kfinance.model.dto.user.request.UserLoginRequest
import com.grdkrll.kfinance.model.dto.user.request.UserRegisterRequest
import com.grdkrll.kfinance.remote.service.user.UserService
import io.ktor.client.*
import io.ktor.client.request.*
import io.ktor.http.*

class UserServiceImpl (
    private val client: HttpClient
) : UserService {
    companion object {
        private const val BASE_URL = "http://angry-clouds-cheat-194-186-53-186.loca.lt"
    }

    override suspend fun loginUser(email: String, password: String) = client.post("$BASE_URL/u/sign_in") {
        contentType(ContentType.Application.Json)
        setBody(UserLoginRequest(email, password))
    }

    override suspend fun registerUser(email: String, password: String) = client.post("$BASE_URL/u/sign_up") {
        contentType(ContentType.Application.Json)
        setBody(UserRegisterRequest(email, email, password))
    }
}