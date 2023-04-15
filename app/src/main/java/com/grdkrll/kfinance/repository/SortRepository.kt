package com.grdkrll.kfinance.repository

import android.content.Context
import com.grdkrll.kfinance.R

enum class SortType {
    NEW,
    OLD,
    RISING,
    FALLING
}

class SortRepository(context: Context) {
    private var prefs = context.getSharedPreferences(context.getString(R.string.app_name), Context.MODE_PRIVATE)
    companion object {
        const val SORT_TYPE = "sort_type"
    }

    fun saveSortingType(sortType: String) {
        val editor = prefs.edit()
        editor.putString(SORT_TYPE, sortType)
        editor.apply()
    }

    fun fetchSortType() = SortType.valueOf(prefs.getString(SORT_TYPE, null) ?: "NEW")
}