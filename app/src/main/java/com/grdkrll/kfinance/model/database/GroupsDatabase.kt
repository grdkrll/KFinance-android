package com.grdkrll.kfinance.model.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.grdkrll.kfinance.model.dao.GroupsDao
import com.grdkrll.kfinance.model.table.GroupEntity

/**
 * An abstract class of Room Database used to store Groups which User created or in which user joined
 */
@Database(entities = [GroupEntity::class], version = 1, exportSchema = false)
abstract class GroupsDatabase : RoomDatabase() {
    /**
     * Returns an instance of DAO to communicate with the local database
     *
     * @return an instance of DAO to communicate with the local database
     */
    abstract fun getGroupsDao() : GroupsDao
}