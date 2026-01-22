package com.gaugustini.mhfudatabase.data.database.dao

import androidx.room.Dao
import androidx.room.Query
import com.gaugustini.mhfudatabase.data.database.entity.monster.MonsterItemEntity
import com.gaugustini.mhfudatabase.data.database.entity.monster.MonsterStatusEntity
import com.gaugustini.mhfudatabase.data.database.relation.MonsterHitzone
import com.gaugustini.mhfudatabase.data.database.relation.MonsterRewardItem
import com.gaugustini.mhfudatabase.data.database.relation.MonsterWithText
import com.gaugustini.mhfudatabase.data.database.relation.QuestWithText

/**
 * [Dao] for Monster related database operations.
 */
@Dao
interface MonsterDao {

    @Query(
        """
        SELECT monster.*, monster_text.* FROM monster
        JOIN monster_text
            ON monster.id = monster_text.monster_id
            AND monster_text.language = :language
        WHERE monster.id = :monsterId
        """
    )
    suspend fun getMonster(monsterId: Int, language: String): MonsterWithText

    @Query(
        """
        SELECT monster.*, monster_text.* FROM monster
        JOIN monster_text
            ON monster.id = monster_text.monster_id
            AND monster_text.language = :language
        """
    )
    suspend fun getMonsterList(language: String): List<MonsterWithText>

    @Query(
        """
        SELECT monster_hitzone.*, hitzone_text.* FROM monster_hitzone
        JOIN hitzone_text
            ON monster_hitzone.hitzone_id = hitzone_text.hitzone_id
            AND hitzone_text.language = :language
        WHERE monster_hitzone.monster_id = :monsterId
        """
    )
    suspend fun getHitzonesByMonsterId(monsterId: Int, language: String): List<MonsterHitzone>

    @Query("SELECT * FROM monster_status WHERE monster_id = :monsterId")
    suspend fun getMonsterStatusByMonsterId(monsterId: Int): List<MonsterStatusEntity>

    @Query("SELECT * FROM monster_item WHERE monster_id = :monsterId")
    suspend fun getMonsterItemByMonsterId(monsterId: Int): MonsterItemEntity

    @Query(
        """
        SELECT
            monster_reward.*,
            reward_condition_text.*,
            item.*,
            item_text.*
        FROM monster_reward
        JOIN reward_condition_text
            ON monster_reward.reward_condition_id = reward_condition_text.reward_condition_id
            AND reward_condition_text.language = :language
        JOIN item
            ON monster_reward.item_id = item.id
        JOIN item_text
            ON item.id = item_text.item_id
            AND item_text.language = :language
        WHERE monster_id = :monsterId
        """
    )
    suspend fun getMonsterRewardsByMonsterId(monsterId: Int, language: String): List<MonsterRewardItem>

    @Query(
        """
        SELECT quest.*, quest_text.*
        FROM quest_monster
        JOIN quest
            ON quest_monster.quest_id = quest.id
        JOIN quest_text
            ON quest.id = quest_text.quest_id
            AND quest_text.language = :language
        WHERE monster_id = :monsterId
        """
    )
    suspend fun getQuestsByMonsterId(monsterId: Int, language: String): List<QuestWithText>

}
