package com.gaugustini.mhfudatabase.data.database.dao

import androidx.room.Dao
import androidx.room.Query
import com.gaugustini.mhfudatabase.data.model.Monster
import com.gaugustini.mhfudatabase.data.model.Quest

@Dao
interface QuestDao {

    // List

    @Query(
        """
        SELECT
            quest.id               AS id,
            quest_text.name        AS name,
            quest_text.goal        AS goal,
            quest.goal_type        AS goalType,
            quest_text.client      AS client,
            quest_text.description AS description,
            quest.hub_type         AS hubType,
            quest.stars            AS stars,
            quest.quest_type       AS questType,
            quest.reward           AS reward,
            quest.fee              AS fee,
            quest.time_limit       AS timeLimit,
            quest.location_id      AS locationId,
            location_text.name     AS locationName
        FROM quest
        JOIN quest_text
            ON quest.id = quest_text.quest_id
        JOIN location_text
            ON quest.location_id = location_text.location_id
        WHERE
            quest_text.language = :language AND
            location_text.language = :language
        """
    )
    suspend fun getQuestList(language: String): List<Quest>

    // Detail

    @Query(
        """
        SELECT
            quest.id               AS id,
            quest_text.name        AS name,
            quest_text.goal        AS goal,
            quest.goal_type        AS goalType,
            quest_text.client      AS client,
            quest_text.description AS description,
            quest.hub_type         AS hubType,
            quest.stars            AS stars,
            quest.quest_type       AS questType,
            quest.reward           AS reward,
            quest.fee              AS fee,
            quest.time_limit       AS timeLimit,
            quest.location_id      AS locationId,
            location_text.name     AS locationName
        FROM quest
        JOIN quest_text
            ON quest.id = quest_text.quest_id
        JOIN location_text
            ON quest.location_id = location_text.location_id
        WHERE
            quest.id = :id AND
            quest_text.language = :language AND
            location_text.language = :language
        """
    )
    suspend fun getQuest(id: Int, language: String): Quest

    @Query(
        """
        SELECT
            monster.id                  AS id,
            monster_text.name           AS name,
            monster_text.ecology        AS ecology,
            monster_text.description    AS description,
            monster.monster_type        AS type,
            monster.golden_largest_min  AS sizeLargestMin,
            monster.golden_largest_max  AS sizeLargestMax,
            monster.golden_smallest_min AS sizeSmallestMin,
            monster.golden_smallest_max AS sizeSmallestMax
        FROM quest_monster
        JOIN monster
            ON quest_monster.monster_id = monster.id
        JOIN monster_text
            ON quest_monster.monster_id = monster_text.monster_id
        WHERE
            quest_monster.quest_id = :id AND
            monster_text.language = :language
        """
    )
    suspend fun getMonstersForQuest(id: Int, language: String): List<Monster>

}
