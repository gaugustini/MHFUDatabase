package com.gaugustini.mhfudatabase.data.database.entity.quest

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import com.gaugustini.mhfudatabase.data.database.entity.monster.MonsterEntity

@Entity(
    tableName = "quest_monster",
    primaryKeys = ["quest_id", "monster_id"],
    foreignKeys = [
        ForeignKey(
            entity = QuestEntity::class,
            parentColumns = ["id"],
            childColumns = ["quest_id"],
        ),
        ForeignKey(
            entity = MonsterEntity::class,
            parentColumns = ["id"],
            childColumns = ["monster_id"],
        ),
    ],
    indices = [
        Index(value = ["monster_id"]),
    ],
)
data class QuestMonsterEntity(
    @ColumnInfo(name = "quest_id") val questId: Int,
    @ColumnInfo(name = "monster_id") val monsterId: Int,
)
