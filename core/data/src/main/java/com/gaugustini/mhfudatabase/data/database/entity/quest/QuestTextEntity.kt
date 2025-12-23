package com.gaugustini.mhfudatabase.data.database.entity.quest

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey

@Entity(
    tableName = "quest_text",
    primaryKeys = ["quest_id", "language"],
    foreignKeys = [
        ForeignKey(
            entity = QuestEntity::class,
            parentColumns = ["id"],
            childColumns = ["quest_id"],
        ),
    ],
)
data class QuestTextEntity(
    @ColumnInfo(name = "quest_id") val questId: Int,
    @ColumnInfo(name = "language") val language: String,
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "goal") val goal: String,
    @ColumnInfo(name = "client") val client: String,
    @ColumnInfo(name = "description") val description: String,
)
