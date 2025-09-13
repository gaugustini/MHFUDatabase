package com.gaugustini.mhfudatabase.data.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index

@Entity(
    tableName = "monster",
    primaryKeys = ["id"]
)
data class MonsterEntity(
    @ColumnInfo(name = "id") val id: Int,
    @ColumnInfo(name = "monster_type") val monsterType: String,
    @ColumnInfo(name = "golden_smallest_min") val sizeSmallestMin: Int?,
    @ColumnInfo(name = "golden_smallest_max") val sizeSmallestMax: Int?,
    @ColumnInfo(name = "golden_largest_min") val sizeLargestMin: Int?,
    @ColumnInfo(name = "golden_largest_max") val sizeLargestMax: Int?,
)

@Entity(
    tableName = "monster_text",
    primaryKeys = ["monster_id", "language"],
    foreignKeys = [
        ForeignKey(
            entity = MonsterEntity::class,
            parentColumns = ["id"],
            childColumns = ["monster_id"]
        )
    ]
)
data class MonsterTextEntity(
    @ColumnInfo(name = "monster_id") val monsterId: Int,
    @ColumnInfo(name = "language") val language: String,
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "ecology") val ecology: String,
    @ColumnInfo(name = "description") val description: String,
)

@Entity(
    tableName = "hitzone",
    primaryKeys = ["id"]
)
data class HitzoneEntity(
    @ColumnInfo(name = "id") val id: Int,
)

@Entity(
    tableName = "hitzone_text",
    primaryKeys = ["hitzone_id", "language"],
    foreignKeys = [
        ForeignKey(
            entity = HitzoneEntity::class,
            parentColumns = ["id"],
            childColumns = ["hitzone_id"]
        )
    ]
)
data class HitzoneTextEntity(
    @ColumnInfo(name = "hitzone_id") val hitzoneId: Int,
    @ColumnInfo(name = "language") val language: String,
    @ColumnInfo(name = "name") val name: String,
)

@Entity(
    tableName = "monster_hitzone",
    primaryKeys = ["monster_id", "hitzone_id"],
    foreignKeys = [
        ForeignKey(
            entity = MonsterEntity::class,
            parentColumns = ["id"],
            childColumns = ["monster_id"]
        ),
        ForeignKey(
            entity = HitzoneEntity::class,
            parentColumns = ["id"],
            childColumns = ["hitzone_id"]
        )
    ],
    indices = [
        Index(value = ["hitzone_id"])
    ]
)
data class MonsterHitzoneEntity(
    @ColumnInfo(name = "monster_id") val monsterId: Int,
    @ColumnInfo(name = "hitzone_id") val hitzoneId: Int,
    @ColumnInfo(name = "cut") val cut: Int,
    @ColumnInfo(name = "impact") val impact: Int,
    @ColumnInfo(name = "shot") val shot: Int,
    @ColumnInfo(name = "fire") val fire: Int,
    @ColumnInfo(name = "water") val water: Int,
    @ColumnInfo(name = "thunder") val thunder: Int,
    @ColumnInfo(name = "ice") val ice: Int,
    @ColumnInfo(name = "dragon") val dragon: Int,
)

@Entity(
    tableName = "monster_status",
    primaryKeys = ["monster_id", "status"],
    foreignKeys = [
        ForeignKey(
            entity = MonsterEntity::class,
            parentColumns = ["id"],
            childColumns = ["monster_id"]
        )
    ]
)
data class MonsterStatusEntity(
    @ColumnInfo(name = "monster_id") val monsterId: Int,
    @ColumnInfo(name = "status") val status: String,
    @ColumnInfo(name = "initial") val initial: Int,
    @ColumnInfo(name = "increase") val increase: Int,
    @ColumnInfo(name = "max") val max: Int,
    @ColumnInfo(name = "duration") val duration: Int,
    @ColumnInfo(name = "damage") val damage: Int,
)

@Entity(
    tableName = "monster_item",
    primaryKeys = ["monster_id", "state"],
    foreignKeys = [
        ForeignKey(
            entity = MonsterEntity::class,
            parentColumns = ["id"],
            childColumns = ["monster_id"]
        )
    ]
)
data class MonsterItemEntity(
    @ColumnInfo(name = "monster_id") val monsterId: Int,
    @ColumnInfo(name = "state") val state: String,
    @ColumnInfo(name = "pitfall_duration") val pitfallDuration: Int?,
    @ColumnInfo(name = "shock_duration") val shockDuration: Int?,
    @ColumnInfo(name = "flash_duration") val flashDuration: Int?,
    @ColumnInfo(name = "sonic_duration") val sonicDuration: Int?,
    @ColumnInfo(name = "dung_bomb") val dungBomb: Boolean,
    @ColumnInfo(name = "meat") val meat: Boolean,
)

@Entity(
    tableName = "reward_condition",
    primaryKeys = ["id"]
)
data class RewardConditionEntity(
    @ColumnInfo(name = "id") val id: Int,
)

@Entity(
    tableName = "reward_condition_text",
    primaryKeys = ["reward_condition_id", "language"],
    foreignKeys = [
        ForeignKey(
            entity = RewardConditionEntity::class,
            parentColumns = ["id"],
            childColumns = ["reward_condition_id"]
        )
    ]
)
data class RewardConditionTextEntity(
    @ColumnInfo(name = "reward_condition_id") val rewardConditionId: Int,
    @ColumnInfo(name = "language") val language: String,
    @ColumnInfo(name = "name") val name: String,
)

@Entity(
    tableName = "monster_reward",
    primaryKeys = ["monster_id", "reward_condition_id", "item_id", "rank", "stack_size"],
    foreignKeys = [
        ForeignKey(
            entity = MonsterEntity::class,
            parentColumns = ["id"],
            childColumns = ["monster_id"]
        ),
        ForeignKey(
            entity = RewardConditionEntity::class,
            parentColumns = ["id"],
            childColumns = ["reward_condition_id"]
        ),
        ForeignKey(
            entity = ItemEntity::class,
            parentColumns = ["id"],
            childColumns = ["item_id"]
        )
    ],
    indices = [
        Index(value = ["reward_condition_id"]),
        Index(value = ["item_id"])
    ]
)
data class MonsterRewardEntity(
    @ColumnInfo(name = "monster_id") val monsterId: Int,
    @ColumnInfo(name = "reward_condition_id") val rewardConditionId: Int,
    @ColumnInfo(name = "item_id") val itemId: Int,
    @ColumnInfo(name = "rank") val rank: String,
    @ColumnInfo(name = "stack_size") val stackSize: Int,
    @ColumnInfo(name = "percentage") val percentage: Int?,
)
