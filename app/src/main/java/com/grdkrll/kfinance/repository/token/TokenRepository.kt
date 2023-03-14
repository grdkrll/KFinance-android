package com.grdkrll.kfinance.repository.token

import android.content.Context
import com.grdkrll.kfinance.R

class TokenRepository(context: Context) {
    private var prefs = context.getSharedPreferences(context.getString(R.string.app_name), Context.MODE_PRIVATE)

    companion object {
        const val USER_TOKEN = "user_token"
    }

    fun saveAuthToken(token: String) {
        val editor = prefs.edit()
        editor.putString(USER_TOKEN, token)
        editor.apply()
    }

    fun fetchAuthToken() = prefs.getString(USER_TOKEN, null)
}