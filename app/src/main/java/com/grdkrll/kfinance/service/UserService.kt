package com.grdkrll.kfinance.service

import io.ktor.client.statement.*

/**
 * A Service Class used to make API calls to the backend about User
 */
interface UserService {
    /**
     * Used to make an API call to the backend to Sign In the User
     *
     * @param email the login of User Account
     * @param password the password of User Account
     */
    suspend fun loginUser(email: String, password: String): HttpResponse

    /**
     * Used to make an API call to the backend to Sign Up the User
     *
     * @param name the name for the User Account
     * @param email the email for the User Account
     * @param handle the handle for the User Account
     * @param password the password for the User Account
     */
    suspend fun registerUser(
        name: String,
        email: String,
        handle: String,
        password: String
    ): HttpResponse

    /**
     * Used to make an API call to the backend to Sign In / Sign Up the User via Google Sign In
     *
     * @param googleIdToken the token that Google Sign In Intent returned
     */
    suspend fun loginWithGoogle(googleIdToken: String): HttpResponse

    /**
     * Used to make an API call to the backend to change User Data
     *
     * @param name a new name for the User
     * @param handle a new handle for the User
     * @param email a new password for the User (leave empty to keep current password)
     * @param confirmPassword the current password of User
     * @param token a JWT token to Authorize the call
     */
    suspend fun changeUserData(
        name: String,
        handle: String,
        email: String,
        password: String,
        confirmPassword: String,
        token: String?
    ): HttpResponse
}