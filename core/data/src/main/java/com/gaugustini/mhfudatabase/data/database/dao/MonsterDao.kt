package com.gaugustini.mhfudatabase.data.database.dao

import androidx.room.Dao
import androidx.room.Query
import com.gaugustini.mhfudatabase.data.database.entity.monster.MonsterItemEntity
import com.gaugustini.mhfudatabase.data.database.entity.monster.MonsterStatusEntity
import com.gaugustini.mhfudatabase.data.database.relation.MonsterHitzoneWithText
import com.gaugustini.mhfudatabase.data.database.relation.MonsterRewardWithItem
import com.gaugustini.mhfudatabase.data.database.relation.MonsterWithText
import com.gaugustini.mhfudatabase.data.database.relation.QuestWithText

/**
 * [Dao] for Monster related database operations.
 */
@Dao
interface MonsterDao {

    @Query(
        """
        SELECT
            monster.*,
            monster_text.*
        FROM monster
        JOIN monster_text
            ON monster.id = monster_text.monster_id
            AND monster_text.language = :language
        WHERE monster.id = :monsterId
        """
    )
    suspend fun getMonster(monsterId: Int, language: String): MonsterWithText

    @Query(
        """
        SELECT
            monster.*,
            monster_text.*
        FROM monster
        JOIN monster_text
            ON monster.id = monster_text.monster_id
            AND monster_text.language = :language
        WHERE
            (:name IS NULL OR (monster_text.name LIKE '%' || :name || '%'))
            AND (:ecology IS NULL OR monster_text.ecology = :ecology)
            AND (:type IS NULL OR monster.monster_type = :type)
        ORDER BY monster_text.name ASC
        """
    )
    suspend fun getMonsterList(
        language: String,
        name: String?,
        ecology: String?,
        type: String?,
    ): List<MonsterWithText>

    @Query(
        """
        SELECT
            monster_hitzone.*,
            htxt.hitzone_id AS htxt_hitzone_id, htxt.language AS htxt_language, htxt.name AS htxt_name
        FROM monster_hitzone
        JOIN hitzone_text htxt
            ON monster_hitzone.hitzone_id = htxt.hitzone_id
            AND htxt.language = :language
        WHERE monster_hitzone.monster_id = :monsterId
        """
    )
    suspend fun getHitzonesByMonsterId(monsterId: Int, language: String): List<MonsterHitzoneWithText>

    @Query(
        """
        SELECT
            monster_status.*
        FROM monster_status
        WHERE monster_id = :monsterId
        """
    )
    suspend fun getMonsterStatusByMonsterId(monsterId: Int): List<MonsterStatusEntity>

    @Query(
        """
        SELECT 
            monster_item.*
        FROM monster_item
        WHERE monster_id = :monsterId
        ORDER BY monster_item.state DESC
        """
    )
    suspend fun getMonsterItemByMonsterId(monsterId: Int): List<MonsterItemEntity>

    @Query(
        """
        SELECT
            mr.monster_id AS mr_monster_id, mr.reward_condition_id AS mr_reward_condition_id, mr.item_id AS mr_item_id, mr.rank AS mr_rank, mr.stack_size AS mr_stack_size, mr.percentage AS mr_percentage,
            rctxt.reward_condition_id AS rctxt_reward_condition_id, rctxt.language AS rctxt_language, rctxt.name AS rctxt_name,
            item.*,
            item_text.*
        FROM monster_reward mr
        JOIN reward_condition_text rctxt
            ON mr.reward_condition_id = rctxt.reward_condition_id
            AND rctxt.language = :language
        JOIN item
            ON mr.item_id = item.id
        JOIN item_text
            ON item.id = item_text.item_id
            AND item_text.language = :language
        WHERE monster_id = :monsterId
        ORDER BY rctxt.reward_condition_id ASC, mr.percentage DESC
        """
    )
    suspend fun getMonsterRewardsByMonsterId(
        monsterId: Int,
        language: String
    ): List<MonsterRewardWithItem>

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
