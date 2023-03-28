package com.grdkrll.kfinance.repository

import android.content.Context
import com.grdkrll.kfinance.R
import com.grdkrll.kfinance.model.User
import com.grdkrll.kfinance.model.dto.user.response.UserResponse
import com.grdkrll.kfinance.model.exception.user.SignInException
import com.grdkrll.kfinance.model.exception.user.SignUpException
import com.grdkrll.kfinance.remote.service.user.UserService
import io.ktor.client.call.*


class UserRepository(
    private val userService: UserService,
    private val tokenRepository: TokenRepository,
    private val context: Context
) {
    private var prefs = context.getSharedPreferences(context.getString(R.string.app_name), Context.MODE_PRIVATE)

    companion object {
        const val USER_EMAIL = "user_email"
        const val USER_HANDLE = "user_handle"
    }
    fun getUser() : User? {
        if(tokenRepository.fetchAuthToken() != null) {
            return getUserData()
        }
        return null
    }

    suspend fun loginUser(email: String, password: String): Result<UserResponse> {
        try {
            val res = userService.loginUser(email, password).body<UserResponse>()
            tokenRepository.saveAuthToken(res.token)
            saveUserData(res.email, res.handle)
            return Result.success(res)
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return Result.failure(SignInException("Sign-in failed"))
    }
    suspend fun registerUser(email: String, password: String): Result<UserResponse> {
        try {
            val res = userService.registerUser(email, password).body<UserResponse>()
            tokenRepository.saveAuthToken(res.token)
            saveUserData(res.email, res.handle)
            return Result.success(res)
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return Result.failure(SignUpException("Sign-Up failed"))
    }

    suspend fun loginWithGoogle(googleIdToken: String) : Result<UserResponse> {
        return try {
            val res = userService.loginWithGoogle(googleIdToken).body<UserResponse>()
            tokenRepository.saveAuthToken(res.token)
            saveUserData(res.email, res.handle)
            Result.success(res)
        } catch (e: Exception) {
            e.printStackTrace()
            Result.failure(e)
        }
    }

    private fun saveUserData(email: String, handle: String) {
        val editor = prefs.edit()
        editor.putString(USER_EMAIL, email)
        editor.putString(USER_HANDLE, handle)
        editor.apply()
    }

    private fun getUserData(): User = User(
        email = prefs.getString(USER_EMAIL, null) ?: "email@email.com",
        handle = prefs.getString(USER_HANDLE, null) ?: "handle"
    )
}