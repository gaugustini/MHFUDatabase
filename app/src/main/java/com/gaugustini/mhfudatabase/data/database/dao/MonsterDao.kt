package com.gaugustini.mhfudatabase.data.database.dao

import androidx.room.Dao
import androidx.room.Query
import com.gaugustini.mhfudatabase.data.model.AilmentStatus
import com.gaugustini.mhfudatabase.data.model.Hitzone
import com.gaugustini.mhfudatabase.data.model.ItemUsage
import com.gaugustini.mhfudatabase.data.model.Monster
import com.gaugustini.mhfudatabase.data.model.MonsterReward

@Dao
interface MonsterDao {

    // List

    @Query(
        """
        SELECT
            monster.id                  AS id,
            monster_text.name           AS name,
            monster_text.ecology        AS ecology,
            monster_text.description    AS description,
            monster.monster_type        AS type,
            monster.golden_smallest_min AS sizeSmallestMin,
            monster.golden_smallest_max AS sizeSmallestMax,
            monster.golden_largest_min  AS sizeLargestMin,
            monster.golden_largest_max  AS sizeLargestMax
        FROM monster
        JOIN monster_text
            ON monster.id = monster_text.monster_id
        WHERE
            monster_text.language = :language
        """
    )
    suspend fun getMonsterList(language: String): List<Monster>

    // Detail

    @Query(
        """
        SELECT
            monster.id                  AS id,
            monster_text.name           AS name,
            monster_text.ecology        AS ecology,
            monster_text.description    AS description,
            monster.monster_type        AS type,
            monster.golden_smallest_min AS sizeSmallestMin,
            monster.golden_smallest_max AS sizeSmallestMax,
            monster.golden_largest_min  AS sizeLargestMin,
            monster.golden_largest_max  AS sizeLargestMax
        FROM monster
        JOIN monster_text
            ON monster.id = monster_text.monster_id
        WHERE
            monster.id = :id AND
            monster_text.language = :language
        """
    )
    suspend fun getMonster(id: Int, language: String): Monster

    @Query(
        """
        SELECT
            hitzone_text.name       AS name,
            monster_hitzone.cut     AS cut,
            monster_hitzone.impact  AS impact,
            monster_hitzone.shot    AS shot,
            monster_hitzone.fire    AS fire,
            monster_hitzone.water   AS water,
            monster_hitzone.thunder AS thunder,
            monster_hitzone.ice     AS ice,
            monster_hitzone.dragon  AS dragon
        FROM monster_hitzone
        JOIN hitzone_text
            ON monster_hitzone.hitzone_id = hitzone_text.hitzone_id
        WHERE
            monster_hitzone.monster_id = :id AND
            hitzone_text.language = :language
        """
    )
    suspend fun getHitzonesForMonster(id: Int, language: String): List<Hitzone>

    @Query(
        """
        SELECT
            monster_status.status   AS type,
            monster_status.initial  AS initial,
            monster_status.increase AS increase,
            monster_status.max      AS max,
            monster_status.duration AS duration,
            monster_status.damage   AS damage
        FROM monster_status
        WHERE
            monster_status.monster_id = :id
        """
    )
    suspend fun getAilmentStatusForMonster(id: Int): List<AilmentStatus>

    @Query(
        """
        SELECT
            monster_item.state              AS monsterState,
            monster_item.pitfall            AS canUsePitfallTrap,
            monster_item.pitfall_duration   AS pitfallDuration,
            monster_item.shock              AS canUseShockTrap,
            monster_item.shock_duration     AS shockDuration,
            monster_item.flash              AS canUseFlashBomb,
            monster_item.flash_duration     AS flashDuration,
            monster_item.sonic              AS canUseSonicBomb,
            monster_item.dung               AS canUseDungBomb,
            monster_item.meat               AS canUseMeat
        FROM monster_item
        WHERE
            monster_item.monster_id = :id
        """
    )
    suspend fun getItemUsagesForMonster(id: Int): List<ItemUsage>

    @Query(
        """
        SELECT
            monster_reward.item_id      AS itemId,
            item_text.name              AS itemName,
            item.icon_type              AS itemIconType,
            item.icon_color             AS itemIconColor,
            reward_condition_text.name  AS condition,
            monster_reward.rank         AS `rank`,
            monster_reward.stack_size   AS stackSize,
            monster_reward.percentage   AS percentage
        FROM monster_reward
        JOIN item_text
            ON monster_reward.item_id = item_text.item_id
        JOIN item
            ON monster_reward.item_id = item.id
        JOIN reward_condition_text
            ON monster_reward.reward_condition_id = reward_condition_text.reward_condition_id
        WHERE
            monster_reward.monster_id = :id AND
            item_text.language = :language AND
            reward_condition_text.language = :language
        """
    )
    suspend fun getRewardsForMonster(id: Int, language: String): List<MonsterReward>

}
