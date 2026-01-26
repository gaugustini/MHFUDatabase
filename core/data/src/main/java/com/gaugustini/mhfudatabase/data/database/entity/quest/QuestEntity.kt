package com.gaugustini.mhfudatabase.data.database.entity.quest

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import com.gaugustini.mhfudatabase.data.database.entity.location.LocationEntity

@Entity(
    tableName = "quest",
    primaryKeys = ["id"],
    foreignKeys = [
        ForeignKey(
            entity = LocationEntity::class,
            parentColumns = ["id"],
            childColumns = ["location_id"],
        ),
    ],
    indices = [
        Index(value = ["location_id"]),
    ],
)
data class QuestEntity(
    @ColumnInfo(name = "id") val id: Int,
    @ColumnInfo(name = "location_id") val locationId: Int,
    @ColumnInfo(name = "hub_type") val hubType: String,
    @ColumnInfo(name = "stars") val stars: Int,
    @ColumnInfo(name = "goal_type") val goalType: String,
    @ColumnInfo(name = "quest_type") val questType: String,
    @ColumnInfo(name = "reward") val reward: Int,
    @ColumnInfo(name = "fee") val fee: Int,
    @ColumnInfo(name = "time_limit") val timeLimit: Int,
)
