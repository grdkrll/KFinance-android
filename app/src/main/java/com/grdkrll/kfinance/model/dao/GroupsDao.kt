package com.grdkrll.kfinance.model.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.grdkrll.kfinance.model.dto.groups.response.GroupResponse
import com.grdkrll.kfinance.model.table.GroupEntity

/**
 * DAO of Groups which User created or in which User joined
 */
@Dao
interface GroupsDao {

    /**
     * Adds a new group to the local database
     *
     * @param groupEntity holds data about the new group
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addGroup(groupEntity: GroupEntity)

    /**
     * Returns a List of groups stored in the local database by pages (5 at a time)
     *
     * @param page the page to return
     *
     * @return a List of groups from requested page
     */
    @Query("SELECT * FROM groups LIMIT 5 OFFSET :page * 5")
    suspend fun getGroupsForPage(page: Int): List<GroupResponse>

    /**
     * Returns a total number of groups in the local database
     *
     * @return a total number of groups in the local database
     */
    @Query("SELECT COUNT(*) FROM groups")
    suspend fun getTotalCount(): Int
}