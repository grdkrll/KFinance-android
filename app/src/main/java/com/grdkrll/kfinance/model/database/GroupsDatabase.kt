package com.grdkrll.kfinance.model.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.grdkrll.kfinance.model.dao.GroupsDao
import com.grdkrll.kfinance.model.table.GroupEntity

@Database(entities = [GroupEntity::class], version = 1, exportSchema = false)
abstract class GroupsDatabase : RoomDatabase() {
    abstract fun getGroupsDao() : GroupsDao
}