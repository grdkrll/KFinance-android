package com.grdkrll.kfinance.service.user.impl

import com.grdkrll.kfinance.SERVICE_BASE_URL
import com.grdkrll.kfinance.model.dto.user.request.GoogleLoginRequest
import com.grdkrll.kfinance.model.dto.user.request.UserChangeDataRequest
import com.grdkrll.kfinance.model.dto.user.request.UserLoginRequest
import com.grdkrll.kfinance.model.dto.user.request.UserRegisterRequest
import com.grdkrll.kfinance.service.user.UserService
import io.ktor.client.*
import io.ktor.client.request.*
import io.ktor.http.*
import io.ktor.http.auth.*

class UserServiceImpl (
    private val client: HttpClient
) : UserService {
    companion object {
        private const val BASE_URL = SERVICE_BASE_URL
    }

    override suspend fun loginUser(email: String, password: String) = client.post("$BASE_URL/u/sign_in") {
        contentType(ContentType.Application.Json)
        setBody(UserLoginRequest(email, password))
    }

    override suspend fun registerUser(email: String, password: String) = client.post("$BASE_URL/u/sign_up") {
        contentType(ContentType.Application.Json)
        setBody(UserRegisterRequest(email, email, password))
    }

    override suspend fun loginWithGoogle(googleIdToken: String) = client.post("$BASE_URL/u/google_sign_in") {
        contentType(ContentType.Application.Json)
        setBody(GoogleLoginRequest(googleIdToken))
    }

    override suspend fun changeUserData(
        handle: String,
        email: String,
        password: String,
        confirmPassword: String,
        token: String?
    ) = client.post("$BASE_URL/u/change_data") {
        header(AuthScheme.Bearer, token)
        contentType(ContentType.Application.Json)
        setBody(UserChangeDataRequest(handle, email, password, confirmPassword))
    }
}