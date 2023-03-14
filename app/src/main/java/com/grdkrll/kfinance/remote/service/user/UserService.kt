package com.grdkrll.kfinance.remote.service.user

import com.grdkrll.kfinance.model.responce.user.UserResponce

interface UserService {
    suspend fun loginUser(email: String, password: String)

    suspend fun registerUser(email: String, password: String): UserResponce?
}