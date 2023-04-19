package com.grdkrll.kfinance.repository

import android.content.Context
import com.grdkrll.kfinance.R
import com.grdkrll.kfinance.model.Group


class SelectedGroupRepository(context: Context) {
    private var prefs =
        context.getSharedPreferences(context.getString(R.string.app_name), Context.MODE_PRIVATE)

    companion object {
        const val GROUP_ID = "selected_group_id"
        const val GROUP_NAME = "selected_group_name"
    }

    fun saveGroup(id: Int, name: String) {
        val editor = prefs.edit()
        editor.putInt(GROUP_ID, id)
        editor.putString(GROUP_NAME, name)
        editor.apply()
    }

    fun deselectGroup() {
        val editor = prefs.edit()
        editor.remove(GROUP_ID)
        editor.remove(GROUP_NAME)
        editor.apply()
    }

    fun fetchGroup() = Group(
        id = prefs.getInt(GROUP_ID, -1),
        name = prefs.getString(GROUP_NAME, "") ?: ""
    )
}