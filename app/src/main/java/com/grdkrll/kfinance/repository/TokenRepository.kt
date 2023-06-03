package com.grdkrll.kfinance.repository

import android.content.Context
import com.grdkrll.kfinance.R

/**
 * A SharedPreferences Repository class used to hold the JWT Token used to make API calls to the backend
 */
class TokenRepository(context: Context) {
    private var prefs =
        context.getSharedPreferences(context.getString(R.string.app_name), Context.MODE_PRIVATE)

    companion object {
        const val USER_TOKEN = "user_token"
    }

    /**
     * Used to save the JWT Token
     *
     * @param token the Token to be saved
     */
    fun saveAuthToken(token: String) {
        val editor = prefs.edit()
        editor.putString(USER_TOKEN, token)
        editor.apply()
    }

    /**
     * Used to get the saved JWT Token
     *
     * @return the string that contains the Token if it exists or null otherwise
     */
    fun fetchAuthToken() = prefs.getString(USER_TOKEN, null)
}