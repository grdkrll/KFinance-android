package com.grdkrll.kfinance.repository

import android.content.Context
import com.grdkrll.kfinance.R
import com.grdkrll.kfinance.model.Group
import com.grdkrll.kfinance.model.User
import com.grdkrll.kfinance.model.dto.user.response.UserResponse
import com.grdkrll.kfinance.model.exception.user.SignInException
import com.grdkrll.kfinance.model.exception.user.SignUpException
import com.grdkrll.kfinance.service.UserService
import io.ktor.client.call.*


class UserRepository(
    private val userService: UserService,
    private val tokenRepository: TokenRepository,
    context: Context,
    private val selectedGroupRepository: SelectedGroupRepository
) {
    private var prefs =
        context.getSharedPreferences(context.getString(R.string.app_name), Context.MODE_PRIVATE)

    companion object {
        const val USER_ID = "user_id"
        const val USER_NAME = "user_name"
        const val USER_EMAIL = "user_email"
        const val USER_HANDLE = "user_handle"
    }

    fun getUser(): Pair<User, Group>? {
        if (tokenRepository.fetchAuthToken() != null) {
            return Pair(getUserData(), selectedGroupRepository.fetchGroup())
        }
        return null
    }

    suspend fun loginUser(email: String, password: String): Result<UserResponse> {
        try {
            val res = userService.loginUser(email, password).body<UserResponse>()
            tokenRepository.saveAuthToken(res.token)
            saveUserData(res.id, res.name, res.email, res.handle)
            saveUserData(res.id, res.name, res.email, res.handle)
            return Result.success(res)
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return Result.failure(SignInException("Sign-in failed"))
    }

    suspend fun registerUser(
        name: String,
        email: String,
        handle: String,
        password: String
    ): Result<UserResponse> {
        try {
            val res = userService.registerUser(name, email, handle, password).body<UserResponse>()
            tokenRepository.saveAuthToken(res.token)
            saveUserData(res.id, res.name, res.email, res.handle)
            return Result.success(res)
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return Result.failure(SignUpException("Sign-Up failed"))
    }

    suspend fun loginWithGoogle(googleIdToken: String): Result<UserResponse> {
        return try {
            val res = userService.loginWithGoogle(googleIdToken).body<UserResponse>()
            tokenRepository.saveAuthToken(res.token)
            saveUserData(res.id, res.name, res.email, res.handle)
            Result.success(res)
        } catch (e: Exception) {
            e.printStackTrace()
            Result.failure(e)
        }
    }

    suspend fun changeUserData(
        name: String,
        handle: String,
        email: String,
        password: String,
        confirmPassword: String
    ): Result<UserResponse> {
        try {
            val res = userService.changeUserData(
                name,
                handle,
                email,
                password,
                confirmPassword,
                tokenRepository.fetchAuthToken()
            ).body<UserResponse>()
            tokenRepository.saveAuthToken(res.token)
            saveUserData(res.id, res.name, res.email, res.handle)
            return Result.success(res)
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return Result.failure(SignInException("Data change failed"))
    }

    private fun saveUserData(id: Int, name: String, email: String, handle: String) {
        val editor = prefs.edit()
        editor.putInt(USER_ID, id)
        editor.putString(USER_NAME, name)
        editor.putString(USER_EMAIL, email)
        editor.putString(USER_HANDLE, handle)
        editor.apply()
    }

    private fun getUserData(): User = User(
        id = prefs.getInt(USER_ID, -1),
        name = prefs.getString(USER_NAME, null) ?: "name",
        email = prefs.getString(USER_EMAIL, null) ?: "email@email.com",
        handle = prefs.getString(USER_HANDLE, null) ?: "handle"
    )
}