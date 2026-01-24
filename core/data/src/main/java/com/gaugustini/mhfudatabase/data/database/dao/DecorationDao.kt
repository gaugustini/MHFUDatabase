package com.gaugustini.mhfudatabase.data.database.dao

import androidx.room.Dao
import androidx.room.Query
import com.gaugustini.mhfudatabase.data.database.relation.DecorationWithText
import com.gaugustini.mhfudatabase.data.database.relation.EquipmentItemQuantity
import com.gaugustini.mhfudatabase.data.database.relation.EquipmentSkillTreePoint

/**
 * [Dao] for Decoration related database operations.
 */
@Dao
interface DecorationDao {

    @Query(
        """
        SELECT
            decoration.*,
            item.*,
            item_text.*
        FROM decoration
        JOIN item
            ON decoration.id = item.id
        JOIN item_text
            ON item.id = item_text.item_id
            AND item_text.language = :language
        WHERE decoration.id = :decorationId
        """
    )
    suspend fun getDecoration(decorationId: Int, language: String): DecorationWithText

    @Query(
        """
        SELECT
            decoration.*,
            item.*,
            item_text.*
        FROM decoration
        JOIN item
            ON decoration.id = item.id
        JOIN item_text
            ON item.id = item_text.item_id
            AND item_text.language = :language
        """
    )
    suspend fun getDecorationList(language: String): List<DecorationWithText>

    @Query(
        """
        SELECT 
            decoration_skill.decoration_id AS equipmentId,
            skill_tree.*,
            skill_tree_text.*,
            decoration_skill.point_value AS points
        FROM decoration_skill
        JOIN skill_tree
            ON decoration_skill.skill_tree_id = skill_tree.id
        JOIN skill_tree_text
            ON skill_tree.id = skill_tree_text.skill_tree_id
            AND skill_tree_text.language = :language
        WHERE decoration_skill.decoration_id = :decorationId  
        """
    )
    suspend fun getDecorationSkillsByDecorationId(
        decorationId: Int,
        language: String,
    ): List<EquipmentSkillTreePoint>

    @Query(
        """
        SELECT
            decoration_recipe.decoration_id AS equipmentId,
            item.*,
            item_text.*,
            decoration_recipe.quantity AS quantity
        FROM decoration_recipe
        JOIN item
            ON decoration_recipe.item_id = item.id
        JOIN item_text
            ON item.id = item_text.item_id
            AND item_text.language = :language
        WHERE
            decoration_recipe.decoration_id = :decorationId
            AND decoration_recipe.recipe_variant = :recipeVariant
        """
    )
    suspend fun getDecorationRecipeByDecorationId(
        decorationId: Int,
        recipeVariant: Int,
        language: String,
    ): List<EquipmentItemQuantity>

}
