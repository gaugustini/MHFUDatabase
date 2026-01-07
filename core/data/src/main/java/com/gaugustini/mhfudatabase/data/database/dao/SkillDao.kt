package com.gaugustini.mhfudatabase.data.database.dao

import androidx.room.Dao
import androidx.room.Query
import com.gaugustini.mhfudatabase.data.database.relation.SkillTreeWithText
import com.gaugustini.mhfudatabase.data.database.relation.SkillWithText

/**
 * [Dao] for Skill related database operations.
 */
@Dao
interface SkillDao {

    @Query(
        """
        SELECT skill_tree.*, skill_tree_text.* FROM skill_tree
        JOIN skill_tree_text
            ON skill_tree.id = skill_tree_text.skill_tree_id
            AND skill_tree_text.language = :language
        WHERE skill_tree.id = :skillTreeId
        """
    )
    suspend fun getSkillTree(skillTreeId: Int, language: String): SkillTreeWithText

    @Query(
        """
        SELECT skill_tree.*, skill_tree_text.* FROM skill_tree
        JOIN skill_tree_text
            ON skill_tree.id = skill_tree_text.skill_tree_id
            AND skill_tree_text.language = :language
        """
    )
    suspend fun getSkillTreeList(language: String): List<SkillTreeWithText>

    @Query(
        """
        SELECT skill.*, skill_text.* FROM skill
        JOIN skill_text
            ON skill.id = skill_text.skill_id
            AND skill_text.language = :language
        """
    )
    suspend fun getSkillList(language: String): List<SkillWithText>

    @Query(
        """
        SELECT skill.*, skill_text.* FROM skill
        JOIN skill_text
            ON skill.id = skill_text.skill_id
            AND skill_text.language = :language
        WHERE skill.skill_tree_id = :skillTreeId
        """
    )
    suspend fun getSkillListBySkillTreeId(skillTreeId: Int, language: String): List<SkillWithText>

}
