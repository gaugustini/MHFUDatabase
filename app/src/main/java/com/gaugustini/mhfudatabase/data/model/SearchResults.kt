package com.gaugustini.mhfudatabase.data.model

data class SearchResults(
    val armors: List<Armor> = listOf(),
    val decorations: List<Decoration> = listOf(),
    val items: List<Item> = listOf(),
    val locations: List<Location> = listOf(),
    val monsters: List<Monster> = listOf(),
    val quests: List<Quest> = listOf(),
    val skillTrees: List<SkillTree> = listOf(),
    val skills: List<Skill> = listOf(),
    val weapons: List<Weapon> = listOf(),
)
