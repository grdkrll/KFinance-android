package com.grdkrll.kfinance.repository

import android.content.Context
import com.grdkrll.kfinance.R

/**
 * A SharedPreferences Repository class used to hold data of the group for which User opened the Settings Screen
 *
 * @param context an instance of [Context]
 */
class GroupSettingsRepository(context: Context) {
    private var prefs =
        context.getSharedPreferences(context.getString(R.string.app_name), Context.MODE_PRIVATE)

    companion object {
        const val GROUP_NAME = "group_name"
        const val GROUP_HANDLE = "group_handle"
    }

    /**
     * Used to put data of the Selected Group in the SharedPreferences
     *
     * @param name the name of the Selected Group
     * @param handle the handle of the Selected Group
     */
    fun selectGroup(name: String, handle: String) {
        val editor = prefs.edit()
        editor.putString(GROUP_NAME, name)
        editor.putString(GROUP_HANDLE, handle)
        editor.apply()
    }

    /**
     * Used to remove data of the Selected Group from the SharedPreferences
     */
    fun deselectGroup() {
        val editor = prefs.edit()
        editor.remove(GROUP_NAME)
        editor.remove(GROUP_HANDLE)
        editor.apply()
    }

    /**
     * Used to get data of the Selected Group from the SharedPreferences
     *
     * @return a [Pair] that holds the name and handle of the Selected Group
     */
    fun fetchGroupInfo() =
        Pair(prefs.getString(GROUP_NAME, null), prefs.getString(GROUP_HANDLE, null))
}