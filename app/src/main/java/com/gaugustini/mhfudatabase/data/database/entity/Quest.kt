package com.gaugustini.mhfudatabase.data.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index

@Entity(
    tableName = "quest",
    primaryKeys = ["id"],
    foreignKeys = [
        ForeignKey(
            entity = LocationEntity::class,
            parentColumns = ["id"],
            childColumns = ["location_id"]
        )
    ],
    indices = [
        Index(value = ["location_id"])
    ]
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

@Entity(
    tableName = "quest_text",
    primaryKeys = ["quest_id", "language"],
    foreignKeys = [
        ForeignKey(
            entity = QuestEntity::class,
            parentColumns = ["id"],
            childColumns = ["quest_id"]
        )
    ]
)
data class QuestTextEntity(
    @ColumnInfo(name = "quest_id") val questId: Int,
    @ColumnInfo(name = "language") val language: String,
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "goal") val goal: String,
    @ColumnInfo(name = "client") val client: String,
    @ColumnInfo(name = "description") val description: String,
)

@Entity(
    tableName = "quest_monster",
    primaryKeys = ["quest_id", "monster_id"],
    foreignKeys = [
        ForeignKey(
            entity = QuestEntity::class,
            parentColumns = ["id"],
            childColumns = ["quest_id"]
        ),
        ForeignKey(
            entity = MonsterEntity::class,
            parentColumns = ["id"],
            childColumns = ["monster_id"]
        )
    ],
    indices = [
        Index(value = ["monster_id"])
    ]
)
data class QuestMonsterEntity(
    @ColumnInfo(name = "quest_id") val questId: Int,
    @ColumnInfo(name = "monster_id") val monsterId: Int,
)
