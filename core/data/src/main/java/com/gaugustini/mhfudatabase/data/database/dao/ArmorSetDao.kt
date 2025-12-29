package com.gaugustini.mhfudatabase.data.database.dao

import androidx.room.Dao
import androidx.room.Query
import com.gaugustini.mhfudatabase.data.database.relation.ArmorSetWithText

/**
 * [Dao] for Armor Set related database operations.
 */
@Dao
interface ArmorSetDao {

    @Query(
        """
        SELECT armor_set.*, armor_set_text.* FROM armor_set
        JOIN armor_set_text
            ON armor_set.id = armor_set_text.armor_set_id
            AND armor_set_text.language = :language
        WHERE armor_set.id = :armorSetId
        """
    )
    suspend fun getArmorSet(armorSetId: Int, language: String): ArmorSetWithText

    @Query(
        """
        SELECT armor_set.*, armor_set_text.* FROM armor_set
        JOIN armor_set_text
            ON armor_set.id = armor_set_text.armor_set_id
            AND armor_set_text.language = :language
        """
    )
    suspend fun getArmorSetList(language: String): List<ArmorSetWithText>

}
