package com.gaugustini.mhfudatabase.data.database.dao

import androidx.room.Dao
import androidx.room.Query
import com.gaugustini.mhfudatabase.data.database.relation.ArmorWithSkillTreePoint
import com.gaugustini.mhfudatabase.data.database.relation.DecorationWithSkillTreePoint
import com.gaugustini.mhfudatabase.data.database.relation.SkillTreeWithText
import com.gaugustini.mhfudatabase.data.database.relation.SkillWithText

/**
 * [Dao] for Skill Tree and Skill related database operations.
 */
@Dao
interface SkillDao {

    @Query(
        """
        SELECT
            skill_tree.*,
            skill_tree_text.*
        FROM skill_tree
        JOIN skill_tree_text
            ON skill_tree.id = skill_tree_text.skill_tree_id
            AND skill_tree_text.language = :language
        WHERE skill_tree.id = :skillTreeId
        """
    )
    suspend fun getSkillTree(skillTreeId: Int, language: String): SkillTreeWithText

    @Query(
        """
        SELECT
            skill_tree.*,
            skill_tree_text.*
        FROM skill_tree
        JOIN skill_tree_text
            ON skill_tree.id = skill_tree_text.skill_tree_id
            AND skill_tree_text.language = :language
        WHERE
            (:name IS NULL OR (skill_tree_text.name LIKE '%' || :name || '%' OR skill_tree_text.full_name LIKE '%' || :name || '%'))
            AND (:category IS NULL OR skill_tree.category = :category)
        ORDER BY skill_tree_text.name ASC
        """
    )
    suspend fun getSkillTreeList(
        language: String,
        name: String?,
        category: String?,
    ): List<SkillTreeWithText>

    @Query(
        """
        SELECT
            skill.*,
            skill_text.*
        FROM skill
        JOIN skill_text
            ON skill.id = skill_text.skill_id
            AND skill_text.language = :language
        WHERE skill.skill_tree_id = :skillTreeId
        ORDER BY skill.required_points DESC
        """
    )
    suspend fun getSkillListBySkillTreeId(skillTreeId: Int, language: String): List<SkillWithText>

    @Query(
        """
        SELECT
            skill.*,
            skill_text.*
        FROM skill
        JOIN skill_text 
            ON skill.id = skill_text.skill_id
            AND skill_text.language = :language
        WHERE
            skill.skill_tree_id = :skillTreeId
            AND (
              (:points >= 10 AND skill.required_points > 0 AND skill.required_points <= :points)
              OR 
              (:points <= -10 AND skill.required_points < 0 AND skill.required_points >= :points)
            )
        ORDER BY ABS(skill.required_points) DESC
        LIMIT 1
        """
    )
    suspend fun getActiveSkill(skillTreeId: Int, points: Int, language: String): SkillWithText?

    @Query(
        """
        SELECT
            armor.*,
            armor_text.*,
            sk.id AS sk_id, sk.category AS sk_category,
            sktxt.skill_tree_id AS sktxt_skill_tree_id, sktxt.language AS sktxt_language, sktxt.name AS sktxt_name, sktxt.full_name AS sktxt_full_name,
            armor_skill.point_value AS points
        FROM armor
        JOIN armor_text
            ON armor.id = armor_text.armor_id
            AND armor_text.language = :language
        JOIN armor_skill
            ON armor.id = armor_skill.armor_id
            AND armor_skill.skill_tree_id = :skillTreeId
        JOIN skill_tree sk
            ON armor_skill.skill_tree_id = sk.id
        JOIN skill_tree_text sktxt
            ON sk.id = sktxt.skill_tree_id
            AND sktxt.language = :language
        ORDER BY armor_skill.point_value DESC, armor.rarity ASC
        """
    )
    suspend fun getArmorListWithSkill(skillTreeId: Int, language: String): List<ArmorWithSkillTreePoint>

    @Query(
        """
        SELECT
            decoration.id AS dec_id, decoration.required_slots AS dec_required_slots,
            item.*,
            item_text.*,
            sk.id AS sk_id, sk.category AS sk_category,
            sktxt.skill_tree_id AS sktxt_skill_tree_id, sktxt.language AS sktxt_language, sktxt.name AS sktxt_name, sktxt.full_name AS sktxt_full_name,
            decoration_skill.point_value AS points
        FROM decoration
        JOIN item
            ON decoration.id = item.id
        JOIN item_text
            ON item.id = item_text.item_id
            AND item_text.language = :language
        JOIN decoration_skill
            ON decoration.id = decoration_skill.decoration_id
            AND decoration_skill.skill_tree_id = :skillTreeId
        JOIN skill_tree sk
            ON decoration_skill.skill_tree_id = sk.id
        JOIN skill_tree_text sktxt
            ON sk.id = sktxt.skill_tree_id
            AND sktxt.language = :language
        ORDER BY decoration_skill.point_value DESC
        """
    )
    suspend fun getDecorationListWithSkill(
        skillTreeId: Int,
        language: String
    ): List<DecorationWithSkillTreePoint>

}
