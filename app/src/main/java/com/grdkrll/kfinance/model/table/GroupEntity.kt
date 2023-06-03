package com.grdkrll.kfinance.model.table

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * An Entity class used for storing Groups
 *
 * @property id the id of the Group in the backend database
 * @property name the name of the Group in the backend database
 * @property handle the handle of the Group in the backend database
 * @property ownerId the id of the User that owns the Group
 */
@Entity("groups")
data class GroupEntity(
    @PrimaryKey
    @ColumnInfo("id")
    var id: Int,

    @ColumnInfo("name")
    var name: String,

    @ColumnInfo("handle")
    var handle: String,

    @ColumnInfo("ownerId")
    var ownerId: Int
)