package com.gaugustini.mhfudatabase.data.database.dao

import androidx.room.Dao
import androidx.room.Query
import com.gaugustini.mhfudatabase.data.model.Skill
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
        JOIN skill_tree
            ON skill.skill_tree_id = skill_tree.id
        WHERE
            skill.skill_tree_id = :id AND
            skill_text.language = :language
        ORDER BY requiredPoints DESC
        """
    )
    suspend fun getSkillsForSkillTree(id: Int, language: String): List<Skill>

}
