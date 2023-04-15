package com.grdkrll.kfinance.model.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.grdkrll.kfinance.model.dto.groups.response.GroupResponse
import com.grdkrll.kfinance.model.table.GroupEntity

@Dao
interface GroupsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addGroup(groupEntity: GroupEntity)

    @Query("SELECT * FROM groups LIMIT 5 OFFSET :page * 5")
    suspend fun getGroupsForPage(page: Int): List<GroupResponse>

    @Query("SELECT COUNT(*) FROM groups")
    suspend fun getTotalCount(): Int
}