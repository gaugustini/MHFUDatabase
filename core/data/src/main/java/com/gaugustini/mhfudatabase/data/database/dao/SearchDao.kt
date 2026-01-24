package com.gaugustini.mhfudatabase.data.database.dao

import androidx.room.Query
import com.gaugustini.mhfudatabase.data.database.relation.ArmorWithText
import com.gaugustini.mhfudatabase.data.database.relation.DecorationWithText
import com.gaugustini.mhfudatabase.data.database.relation.ItemWithText
import com.gaugustini.mhfudatabase.data.database.relation.LocationWithText
import com.gaugustini.mhfudatabase.data.database.relation.MonsterWithText
import com.gaugustini.mhfudatabase.data.database.relation.QuestWithText
import com.gaugustini.mhfudatabase.data.database.relation.SkillTreeWithText
import com.gaugustini.mhfudatabase.data.database.relation.SkillWithText
import com.gaugustini.mhfudatabase.data.database.relation.WeaponWithText

interface SearchDao {

    @Query(
        """
        SELECT
            armor.*,
            armor_text.*
        FROM armor
        JOIN armor_text
            ON armor.id = armor_text.armor_id
            AND armor_text.language = :language
        WHERE
            armor_text.name LIKE '%' || :query || '%'
            OR armor_text.full_name LIKE '%' || :query || '%'
        """
    )
    suspend fun searchArmor(query: String, language: String): List<ArmorWithText>

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
            ON decoration.id = item_text.item_id
            AND item_text.language = :language
        WHERE
            item_text.name LIKE '%' || :query || '%'
            OR item_text.full_name LIKE '%' || :query || '%'
        """
    )
    suspend fun searchDecoration(query: String, language: String): List<DecorationWithText>

    @Query(
        """
        SELECT
            item.*,
            item_text.*
        FROM item
        JOIN item_text
            ON item.id = item_text.item_id
            AND item_text.language = :language
        WHERE
            item_text.name LIKE '%' || :query || '%'
            OR item_text.full_name LIKE '%' || :query || '%'
        """
    )
    suspend fun searchItem(query: String, language: String): List<ItemWithText>

    @Query(
        """
        SELECT
            location.*,
            location_text.*
        FROM location
        JOIN location_text
            ON location.id = location_text.location_id
            AND location_text.language = :language
        WHERE
            location_text.name LIKE '%' || :query || '%'
        """
    )
    suspend fun searchLocation(query: String, language: String): List<LocationWithText>

    @Query(
        """
        SELECT
            monster.*,
            monster_text.*
        FROM monster
        JOIN monster_text
            ON monster.id = monster_text.monster_id
            AND monster_text.language = :language
        WHERE
            monster_text.name LIKE '%' || :query || '%'
        """
    )
    suspend fun searchMonster(query: String, language: String): List<MonsterWithText>

    @Query(
        """
        SELECT
            quest.*,
            quest_text.*
        FROM quest
        JOIN quest_text
            ON quest.id = quest_text.quest_id
            AND quest_text.language = :language
        WHERE
            quest_text.name LIKE '%' || :query || '%'
        """
    )
    suspend fun searchQuest(query: String, language: String): List<QuestWithText>

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
            skill_tree_text.name LIKE '%' || :query || '%'
            OR skill_tree_text.full_name LIKE '%' || :query || '%'
        """
    )
    suspend fun searchSkillTree(query: String, language: String): List<SkillTreeWithText>

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
            skill_text.name LIKE '%' || :query || '%'
            OR skill_text.full_name LIKE '%' || :query || '%'
        """
    )
    suspend fun searchSkill(query: String, language: String): List<SkillWithText>

    @Query(
        """
        SELECT
            weapon.*,
            weapon_text.*
        FROM weapon
        JOIN weapon_text
            ON weapon.id = weapon_text.weapon_id
            AND weapon_text.language = :language
        WHERE
            weapon_text.name LIKE '%' || :query || '%'
            OR weapon_text.full_name LIKE '%' || :query || '%'
        """
    )
    suspend fun searchWeapon(query: String, language: String): List<WeaponWithText>

}
