package com.gaugustini.mhfudatabase.data.database.dao

import androidx.room.Dao
import androidx.room.Query
import com.gaugustini.mhfudatabase.data.database.relation.ArmorSetWithText
import com.gaugustini.mhfudatabase.data.database.relation.EquipmentItemQuantity
import com.gaugustini.mhfudatabase.data.database.relation.EquipmentSkillTreePoint

/**
 * [Dao] for Armor Set related database operations.
 */
@Dao
interface ArmorSetDao {

    @Query(
        """
        SELECT
            armor_set.*,
            armor_set_text.*,
            SUM(armor.defense) AS defense,
            SUM(armor.max_defense) AS maxDefense,
            SUM(armor.fire_res) AS fire,
            SUM(armor.water_res) AS water,
            SUM(armor.thunder_res) AS thunder,
            SUM(armor.ice_res) AS ice,
            SUM(armor.dragon_res) AS dragon 
        FROM armor_set
        JOIN armor_set_text
            ON armor_set.id = armor_set_text.armor_set_id
            AND armor_set_text.language = :language
        JOIN armor
            ON armor_set.id = armor.armor_set_id
        WHERE armor_set.id = :armorSetId
        GROUP BY armor_set.id
        """
    )
    suspend fun getArmorSet(armorSetId: Int, language: String): ArmorSetWithText

    @Query(
        """
        SELECT
            armor_set.*,
            armor_set_text.*,
            SUM(armor.defense) AS defense,
            SUM(armor.max_defense) AS maxDefense,
            SUM(armor.fire_res) AS fire,
            SUM(armor.water_res) AS water,
            SUM(armor.thunder_res) AS thunder,
            SUM(armor.ice_res) AS ice,
            SUM(armor.dragon_res) AS dragon 
        FROM armor_set
        JOIN armor_set_text
            ON armor_set.id = armor_set_text.armor_set_id
            AND armor_set_text.language = :language
        JOIN armor
            ON armor_set.id = armor.armor_set_id
        GROUP BY armor_set.id
        """
    )
    suspend fun getArmorSetList(language: String): List<ArmorSetWithText>

    @Query(
        """
        SELECT
            armor_set.id AS equipmentId,
            skill_tree.*,
            skill_tree_text.*,
            SUM(armor_skill.point_value) AS points
        FROM armor_set
        JOIN armor
            ON armor_set.id = armor.armor_set_id
        JOIN armor_skill
            ON armor.id = armor_skill.armor_id
        JOIN skill_tree
            ON armor_skill.skill_tree_id = skill_tree.id
        JOIN skill_tree_text
            ON skill_tree.id = skill_tree_text.skill_tree_id
            AND skill_tree_text.language = :language
        WHERE armor_set.id = :armorSetId
        GROUP BY skill_tree.id
        ORDER BY points DESC
        """
    )
    suspend fun getArmorSetSkillsByArmorSetId(
        armorSetId: Int,
        language: String,
    ): List<EquipmentSkillTreePoint>

    @Query(
        """
        SELECT
            armor_set.id AS equipmentId,
            item.*,
            item_text.*,
            SUM(armor_recipe.quantity) AS quantity
        FROM armor_set
        JOIN armor
            ON armor_set.id = armor.armor_set_id
        JOIN armor_recipe
            ON armor.id = armor_recipe.armor_id
        JOIN item
            ON armor_recipe.item_id = item.id
        JOIN item_text
            ON item.id = item_text.item_id
            AND item_text.language = :language
        WHERE armor_set.id = :armorSetId
        GROUP BY item.id
        ORDER BY quantity DESC
        """
    )
    suspend fun getArmorSetRecipeByArmorSetId(
        armorSetId: Int,
        language: String,
    ): List<EquipmentItemQuantity>

}
