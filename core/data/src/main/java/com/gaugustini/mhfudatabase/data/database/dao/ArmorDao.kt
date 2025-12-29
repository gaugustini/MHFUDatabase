package com.gaugustini.mhfudatabase.data.database.dao

import androidx.room.Dao
import androidx.room.Query
import com.gaugustini.mhfudatabase.data.database.relation.ArmorWithText

/**
 * [Dao] for Armor related database operations.
 */
@Dao
interface ArmorDao {

    @Query(
        """
        SELECT armor.*, armor_text.* FROM armor
        JOIN armor_text
            ON armor.id = armor_text.armor_id
            AND armor_text.language = :language
        WHERE armor.id = :armorId
        """
    )
    suspend fun getArmor(armorId: Int, language: String): ArmorWithText

    @Query(
        """
        SELECT armor.*, armor_text.* FROM armor
        JOIN armor_text
            ON armor.id = armor_text.armor_id
            AND armor_text.language = :language
        """
    )
    suspend fun getArmorList(language: String): List<ArmorWithText>

    @Query(
        """
        SELECT armor.*, armor_text.* FROM armor
        JOIN armor_text
            ON armor.id = armor_text.armor_id
            AND armor_text.language = :language
        WHERE armor.armor_set_id = :armorSetId
        """
    )
    suspend fun getArmorListByArmorSetId(armorSetId: Int, language: String): List<ArmorWithText>

}
