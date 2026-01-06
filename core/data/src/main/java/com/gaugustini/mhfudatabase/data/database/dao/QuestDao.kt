package com.gaugustini.mhfudatabase.data.database.dao

import androidx.room.Dao
import androidx.room.Query
import com.gaugustini.mhfudatabase.data.database.relation.QuestWithText

/**
 * [Dao] for Quest related database operations.
 */
@Dao
interface QuestDao {

    @Query(
        """
        SELECT quest.*, quest_text.* FROM quest
        JOIN quest_text
            ON quest.id = quest_text.quest_id
            AND quest_text.language = :language
        WHERE quest.id = :questId
        """
    )
    suspend fun getQuest(questId: Int, language: String): QuestWithText

    @Query(
        """
        SELECT quest.*, quest_text.* FROM quest
        JOIN quest_text
            ON quest.id = quest_text.quest_id
            AND quest_text.language = :language
        """
    )
    suspend fun getQuestList(language: String): List<QuestWithText>

}
