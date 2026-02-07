package com.gaugustini.mhfudatabase.data.database.dao

import androidx.room.Dao
import androidx.room.Query
import com.gaugustini.mhfudatabase.data.database.relation.LocationWithText
import com.gaugustini.mhfudatabase.data.database.relation.MonsterWithText
import com.gaugustini.mhfudatabase.data.database.relation.QuestWithText

/**
 * [Dao] for Quest related database operations.
 */
@Dao
interface QuestDao {

    @Query(
        """
        SELECT
            quest.*,
            quest_text.*
        FROM quest
        JOIN quest_text
            ON quest.id = quest_text.quest_id
            AND quest_text.language = :language
        WHERE quest.id = :questId
        """
    )
    suspend fun getQuest(questId: Int, language: String): QuestWithText

    @Query(
        """
        SELECT
            quest.*,
            quest_text.*
        FROM quest
        JOIN quest_text
            ON quest.id = quest_text.quest_id
            AND quest_text.language = :language
        WHERE
            (:name IS NULL OR (quest_text.name LIKE '%' || :name || '%'))
            AND (:hub IS NULL OR quest.hub_type = :hub)
            AND (:hasStarFilter = 0 OR quest.stars IN (:stars))
            AND (:type IS NULL OR quest.quest_type = :type)
            AND (:goal IS NULL OR quest.goal_type = :goal)
            AND (:hasLocationFilter = 0 OR quest.location_id IN (:locations))
        """
    )
    suspend fun getQuestList(
        language: String,
        name: String?,
        hub: String?,
        stars: List<Int>?,
        hasStarFilter: Boolean,
        type: String?,
        goal: String?,
        locations: List<Int>?,
        hasLocationFilter: Boolean,
    ): List<QuestWithText>

    @Query(
        """
        SELECT
            location.*,
            location_text.*
        FROM quest
        JOIN location
            ON quest.location_id = location.id
        JOIN location_text
            ON location.id = location_text.location_id
            AND location_text.language = :language
        WHERE quest.id = :questId
        """
    )
    suspend fun getLocationByQuestId(questId: Int, language: String): LocationWithText

    @Query(
        """
        SELECT
            monster.*,
            monster_text.*
        FROM quest_monster
        JOIN monster
            ON quest_monster.monster_id = monster.id
        JOIN monster_text
            ON monster.id = monster_text.monster_id
            AND monster_text.language = :language
        WHERE quest_monster.quest_id = :questId
        """
    )
    suspend fun getMonstersByQuestId(questId: Int, language: String): List<MonsterWithText>

}
