package com.grdkrll.kfinance.model.table

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity("groups")
data class GroupEntity(
    @PrimaryKey
    @ColumnInfo("id")
    var id: Int,

    @ColumnInfo("name")
    var name: String,

    @ColumnInfo("handle")
    var handle: String
)