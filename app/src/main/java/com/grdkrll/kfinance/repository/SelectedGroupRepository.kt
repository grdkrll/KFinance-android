package com.grdkrll.kfinance.repository

import android.content.Context
import com.grdkrll.kfinance.R
import com.grdkrll.kfinance.model.Group

/**
 * A SharedPreferences Repository class used to hold data of the Selected Group
 *
 * @param context an instance of [Context]
 */
class SelectedGroupRepository(context: Context) {
    private var prefs =
        context.getSharedPreferences(context.getString(R.string.app_name), Context.MODE_PRIVATE)

    companion object {
        const val GROUP_ID = "selected_group_id"
        const val GROUP_NAME = "selected_group_name"
    }

    /**
     * Used to put data of the Selected Group in the SharedPreferences
     *
     * @param id the name of the Selected Group
     * @param name the handle of the Selected Group
     */
    fun saveGroup(id: Int, name: String) {
        val editor = prefs.edit()
        editor.putInt(GROUP_ID, id)
        editor.putString(GROUP_NAME, name)
        editor.apply()
    }

    /**
     * Used to remove data of the Selected Group from the SharedPreferences
     */
    fun deselectGroup() {
        val editor = prefs.edit()
        editor.remove(GROUP_ID)
        editor.remove(GROUP_NAME)
        editor.apply()
    }

    /**
     * Used to get data of the Selected Group from the SharedPreferences
     *
     * @return an instance of [Group] that holds data of the Selected Group
     */
    fun fetchGroup() = Group(
        id = prefs.getInt(GROUP_ID, -1),
        name = prefs.getString(GROUP_NAME, "") ?: ""
    )
}