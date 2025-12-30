package com.gaugustini.mhfudatabase.data.database.dao

import androidx.room.Dao
import androidx.room.Query
import com.gaugustini.mhfudatabase.data.database.relation.MonsterWithText

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

}
