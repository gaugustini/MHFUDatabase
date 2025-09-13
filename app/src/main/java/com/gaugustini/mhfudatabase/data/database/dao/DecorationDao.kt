package com.gaugustini.mhfudatabase.data.database.dao

import androidx.room.Dao
import androidx.room.Query
import com.gaugustini.mhfudatabase.data.model.Decoration
import com.gaugustini.mhfudatabase.data.model.ItemQuantity
import com.gaugustini.mhfudatabase.data.model.SkillTreePoints

@Dao
interface DecorationDao {

    // List

    @Query(
        """
        SELECT
            decoration.id               AS id,
            item_text.name              AS name,
            item_text.description       AS description,
            item.rarity                 AS rarity,
            decoration.required_slots   AS requiredSlots,
            item.buy_price              AS buyPrice,
            item.sell_price             AS sellPrice,
            item.icon_type              AS iconType,
            item.icon_color             AS iconColor
        FROM decoration
        JOIN item
            ON decoration.id = item.id
        JOIN item_text
            ON decoration.id = item_text.item_id
        WHERE
            item_text.language = :language
        """
    )
    suspend fun getDecorationList(language: String): List<Decoration>

    // Detail

    @Query(
        """
        SELECT
            decoration.id               AS id,
            item_text.name              AS name,
            item_text.description       AS description,
            item.rarity                 AS rarity,
            decoration.required_slots   AS requiredSlots,
            item.buy_price              AS buyPrice,
            item.sell_price             AS sellPrice,
            item.icon_type              AS iconType,
            item.icon_color             AS iconColor
        FROM decoration
        JOIN item
            ON decoration.id = item.id
        JOIN item_text
            ON decoration.id = item_text.item_id
        WHERE
            decoration.id = :id AND
            item_text.language = :language
        """
    )
    suspend fun getDecoration(id: Int, language: String): Decoration

    @Query(
        """
        SELECT
            decoration_skill.skill_tree_id  AS id,
            skill_tree_text.name            AS name,
            decoration_skill.point_value    AS pointValue
        FROM decoration_skill
        JOIN skill_tree_text
            ON decoration_skill.skill_tree_id = skill_tree_text.skill_tree_id
        WHERE
            decoration_skill.decoration_id = :id AND
            skill_tree_text.language = :language
        """
    )
    suspend fun getSkillsForDecoration(id: Int, language: String): List<SkillTreePoints>

    @Query(
        """
        SELECT
            decoration_recipe.item_id   AS id,
            item_text.name              AS name,
            decoration_recipe.quantity  AS quantity,
            item.icon_type              AS iconType,
            item.icon_color             AS iconColor
        FROM decoration_recipe
        JOIN item
            ON decoration_recipe.item_id = item.id
        JOIN item_text
            ON decoration_recipe.item_id = item_text.item_id
        WHERE
            decoration_recipe.decoration_id = :id AND
            decoration_recipe.recipe_variant = :variant AND
            item_text.language = :language
        """
    )
    suspend fun getItemsForDecoration(id: Int, variant: Int, language: String): List<ItemQuantity>

}
