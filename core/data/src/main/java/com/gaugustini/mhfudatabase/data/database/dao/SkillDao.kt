package com.gaugustini.mhfudatabase.data.database.dao

import androidx.room.Dao
import androidx.room.Query
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
        ORDER BY skill_tree_text.name ASC
        """
    )
    suspend fun getSkillTreeList(language: String): List<SkillTreeWithText>

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

}
