package com.grdkrll.kfinance.remote.service.user.impl

import com.grdkrll.kfinance.model.responce.user.UserResponce
import com.grdkrll.kfinance.remote.service.user.UserService
import io.ktor.client.*
import io.ktor.client.request.*

class UserServiceImpl (
    private val client: HttpClient
) : UserService {
    companion object {
        private const val BASE_URL = ""
    }

    override suspend fun loginUser(email: String, password: String) {
        val res = client.post(HttpRequestBuilder(BASE_URL, ""))
        return
    }

    override suspend fun registerUser(email: String, password: String): UserResponce? {
        val res = client.post(HttpRequestBuilder(BASE_URL, ""))
        return null
    }
}