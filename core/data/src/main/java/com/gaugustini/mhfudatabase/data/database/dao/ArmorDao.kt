package com.gaugustini.mhfudatabase.data.database.dao

import androidx.room.Dao
import androidx.room.Query
import com.gaugustini.mhfudatabase.data.database.relation.ArmorWithText
import com.gaugustini.mhfudatabase.data.database.relation.EquipmentItemQuantity
import com.gaugustini.mhfudatabase.data.database.relation.EquipmentSkillTreePoint

/**
 * [Dao] for Armor related database operations.
 */
@Dao
interface ArmorDao {

    @Query(
        """
        SELECT
            armor.*,
            armor_text.*
        FROM armor
        JOIN armor_text
            ON armor.id = armor_text.armor_id
            AND armor_text.language = :language
        WHERE armor.id = :armorId
        """
    )
    suspend fun getArmor(armorId: Int, language: String): ArmorWithText

    @Query(
        """
        SELECT
            armor.*,
            armor_text.*
        FROM armor
        JOIN armor_text
            ON armor.id = armor_text.armor_id
            AND armor_text.language = :language
        """
    )
    suspend fun getArmorList(language: String): List<ArmorWithText>

    @Query(
        """
        SELECT
            armor.*,
            armor_text.*
        FROM armor
        JOIN armor_text
            ON armor.id = armor_text.armor_id
            AND armor_text.language = :language
        WHERE armor.armor_set_id = :armorSetId
        """
    )
    suspend fun getArmorListByArmorSetId(armorSetId: Int, language: String): List<ArmorWithText>

    @Query(
        """
        SELECT 
            armor_skill.armor_id AS equipmentId,
            skill_tree.*,
            skill_tree_text.*,
            armor_skill.point_value AS points
        FROM armor_skill
        JOIN skill_tree
            ON armor_skill.skill_tree_id = skill_tree.id
        JOIN skill_tree_text
            ON skill_tree.id = skill_tree_text.skill_tree_id
            AND skill_tree_text.language = :language
        WHERE armor_skill.armor_id = :armorId  
        """
    )
    suspend fun getArmorSkillsByArmorId(armorId: Int, language: String): List<EquipmentSkillTreePoint>

    @Query(
        """
        SELECT 
            armor_recipe.armor_id AS equipmentId,
            item.*,
            item_text.*,
            armor_recipe.quantity AS quantity
        FROM armor_recipe
        JOIN item
            ON armor_recipe.item_id = item.id
        JOIN item_text
            ON item.id = item_text.item_id
            AND item_text.language = :language
        WHERE armor_recipe.armor_id = :armorId
        """
    )
    suspend fun getArmorRecipeByArmorId(armorId: Int, language: String): List<EquipmentItemQuantity>

}
