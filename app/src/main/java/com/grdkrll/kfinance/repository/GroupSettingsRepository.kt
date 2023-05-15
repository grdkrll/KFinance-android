package com.grdkrll.kfinance.repository

import android.content.Context
import com.grdkrll.kfinance.R

class GroupSettingsRepository(context: Context) {
    private var prefs =
        context.getSharedPreferences(context.getString(R.string.app_name), Context.MODE_PRIVATE)

    companion object {
        const val GROUP_NAME = "group_name"
        const val GROUP_HANDLE = "group_handle"
    }

    fun selectGroup(name: String, handle: String) {
        val editor = prefs.edit()
        editor.putString(GROUP_NAME, name)
        editor.putString(GROUP_HANDLE, handle)
        editor.apply()
    }

    fun deselectGroup() {
        val editor = prefs.edit()
        editor.remove(GROUP_NAME)
        editor.remove(GROUP_HANDLE)
    }

    fun fetchGroupInfo() =
        Pair(prefs.getString(GROUP_NAME, null), prefs.getString(GROUP_HANDLE, null))
}