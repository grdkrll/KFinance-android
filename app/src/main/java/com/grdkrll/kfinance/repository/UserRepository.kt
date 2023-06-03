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

/**
 * A Repository class used to make calls about User to the local database and the backend
 *
 * @param userService an instance of [UserService]
 * @param tokenRepository an instance of [TokenRepository]
 * @param context an instance of [Context]
 * @param selectedGroupRepository an instance of [SelectedGroupRepository]
 */
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

    /**
     * Used to get data about the User and the Selected Group
     *
     * @return if the User is not logged in returns null otherwise returns a pair of instances of [User] and [Group] (if no group is selected [Group] holds an id of -1)
     */
    fun getUser(): Pair<User, Group>? {
        if (tokenRepository.fetchAuthToken() != null) {
            return Pair(getUserData(), selectedGroupRepository.fetchGroup())
        }
        return null
    }

    /**
     * Used to make an API call to the backend to Sign In the User
     *
     * @param email the login of the User Account
     * @param password the password of the User Account
     *
     * @return an instance of [Result] that if successful holds an instance of [UserResponse]
     */
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

    /**
     * Used to make an API call to the backend to Sign Up the User
     *
     * @param name the name for the User Account
     * @param email the email for the User Account
     * @param handle the handle for the User Account
     * @param password the password for the User Account
     *
     * @return an instance of [Result] that if successful holds an instance of [UserResponse]
     */
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

    /**
     * Used to make an API call to the backend to Sign In the User via Google One Tap
     *
     * @param googleIdToken the token that was returned by Google Sign In Intent
     *
     * @return an instance of [Result] that if successful holds an instance of [UserResponse]
     */
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

    /**
     * Used to make an API call to the backend to change User Data
     *
     * @param name a new name for the User
     * @param handle a new name for the User
     * @param email a new email for the User
     * @param password a new password for the User (leave empty to keep current password)
     * @param confirmPassword current password of the User
     */
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

    /**
     * Used to save User Data in the SharedPreferences
     *
     * @param id the id of the User in the backend database
     * @param name the name of the User in the backend database
     * @param email the email of the User in the backend database
     * @param handle the handle of the User in the backend database
     */
    private fun saveUserData(id: Int, name: String, email: String, handle: String) {
        val editor = prefs.edit()
        editor.putInt(USER_ID, id)
        editor.putString(USER_NAME, name)
        editor.putString(USER_EMAIL, email)
        editor.putString(USER_HANDLE, handle)
        editor.apply()
    }

    /**
     * Used to get User Data from the SharedPreferences
     *
     * @return an instance of [User] that has an id of -1 if no data is found
     */
    private fun getUserData(): User = User(
        id = prefs.getInt(USER_ID, -1),
        name = prefs.getString(USER_NAME, null) ?: "name",
        email = prefs.getString(USER_EMAIL, null) ?: "email@email.com",
        handle = prefs.getString(USER_HANDLE, null) ?: "handle"
    )
}