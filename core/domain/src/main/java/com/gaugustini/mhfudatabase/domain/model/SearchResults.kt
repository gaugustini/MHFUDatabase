package com.gaugustini.mhfudatabase.domain.model

/**
 * Represents the results of a global search across the database.
 *
 * @property armors A list of armor pieces that match the search query.
 * @property decorations A list of decorations that match the search query.
 * @property items A list of items that match the search query.
 * @property locations A list of locations that match the search query.
 * @property monsters A list of monsters that match the search query.
 * @property quests A list of quests that match the search query.
 * @property skillTrees A list of skill trees that match the search query.
 * @property skills A list of skills that match the search query.
 * @property weapons A list of weapons that match the search query.
 */
data class SearchResults(
    val armors: List<Armor>,
    val decorations: List<Decoration>,
    val items: List<Item>,
    val locations: List<Location>,
    val monsters: List<Monster>,
    val quests: List<Quest>,
    val skillTrees: List<SkillTree>,
    val skills: List<Skill>,
    val weapons: List<Weapon>,
)
