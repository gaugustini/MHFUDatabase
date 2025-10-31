package com.gaugustini.mhfudatabase.data.database.dao

import androidx.room.Dao
import androidx.room.Query
import com.gaugustini.mhfudatabase.data.model.Skill
import com.gaugustini.mhfudatabase.data.model.SkillPointsArmor
import com.gaugustini.mhfudatabase.data.model.SkillPointsDecoration
import com.gaugustini.mhfudatabase.data.model.SkillTree

@Dao
interface SkillDao {

    // List

    @Query(
        """
        SELECT
            skill_tree.id           AS id,
            skill_tree_text.name    AS name,
            skill_tree.category     AS category
        FROM skill_tree
        JOIN skill_tree_text
            ON skill_tree.id = skill_tree_text.skill_tree_id
        WHERE
            skill_tree_text.language = :language
        ORDER BY name ASC
        """
    )
    suspend fun getSkillTreeList(language: String): List<SkillTree>

    // Detail

    @Query(
        """
        SELECT
            skill_tree.id           AS id,
            skill_tree_text.name    AS name,
            skill_tree.category     AS category
        FROM skill_tree
        JOIN skill_tree_text
            ON skill_tree.id = skill_tree_text.skill_tree_id
        WHERE
            skill_tree.id = :id AND
            skill_tree_text.language = :language
        """
    )
    suspend fun getSkillTree(id: Int, language: String): SkillTree

    @Query(
        """
        SELECT
            skill.skill_tree_id     AS skillTreeId,
            skill_text.name         AS name,
            skill_text.description  AS description,
            skill.required_points   AS requiredPoints
        FROM skill
        JOIN skill_text
            ON skill.id = skill_text.skill_id
        WHERE
            skill.skill_tree_id = :id AND
            skill_text.language = :language
        ORDER BY requiredPoints DESC
        """
    )
    suspend fun getSkillsForSkillTree(id: Int, language: String): List<Skill>

    @Query(
        """
        SELECT
            armor.id                    AS armorId,
            armor_text.name             AS armorName,
            armor.armor_type            AS armorType,
            armor.rarity                AS rarity,
            armor_skill.skill_tree_id   AS skillTreeId,
            armor_skill.point_value     AS pointValue
        FROM armor
        JOIN armor_text
            ON armor.id = armor_text.armor_id
        JOIN armor_skill
            ON armor.id = armor_skill.armor_id
        WHERE
            armor_skill.skill_tree_id = :id AND
            armor_skill.point_value > 0 AND
            armor_text.language = :language
        ORDER BY pointValue ASC, rarity ASC
        """
    )
    suspend fun getArmorsWithSkillPoints(id: Int, language: String): List<SkillPointsArmor>

    @Query(
        """
        SELECT
            decoration.id                   AS decorationId,
            item_text.name                  AS decorationName,
            item.icon_color                 AS decorationColor,
            decoration_skill.skill_tree_id  AS skillTreeId,
            decoration_skill.point_value    AS pointValue
        FROM decoration
        JOIN item
            on decoration.id = item.id
        JOIN item_text
            ON decoration.id = item_text.item_id
        JOIN decoration_skill
            ON decoration.id = decoration_skill.decoration_id
        WHERE
            decoration_skill.skill_tree_id = :id AND
            decoration_skill.point_value > 0 AND
            item_text.language = :language
        ORDER BY pointValue ASC
        """
    )
    suspend fun getDecorationsWithSkillPoints(id: Int, language: String): List<SkillPointsDecoration>

}
