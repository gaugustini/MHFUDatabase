package com.gaugustini.mhfudatabase.ui.navigation

object Destinations {

    // Main Routes

    const val SEARCH = "search"
    const val ARMOR_SET_LIST = "armor_set_list"
    const val DECORATION_LIST = "decoration_list"
    const val ITEM_LIST = "item_list"
    const val ITEM_COMBINATION_LIST = "item_combination_list"
    const val LOCATION_LIST = "location_list"
    const val MONSTER_LIST = "monster_list"
    const val QUEST_LIST = "quest_list"
    const val SKILL_TREE_LIST = "skill_tree_list"
    const val WEAPON_TYPE_LIST = "weapon_type_list"
    const val USER_EQUIPMENT_SET_LIST = "user_equipment_set_list"
    const val SETTINGS = "settings"
    const val ABOUT = "about"

    // Detail Routes

    const val ARMOR_DETAIL = "armor_detail/{armorId}"
    const val DECORATION_DETAIL = "decoration_detail/{decorationId}"
    const val ITEM_DETAIL = "item_detail/{itemId}"
    const val LOCATION_DETAIL = "location_detail/{locationId}"
    const val MONSTER_DETAIL = "monster_detail/{monsterId}"
    const val QUEST_DETAIL = "quest_detail/{questId}"
    const val SKILL_TREE_DETAIL = "skill_tree_detail/{skillTreeId}"
    const val WEAPON_TREE = "weapon_tree/{weaponType}"
    const val WEAPON_DETAIL = "weapon_detail/{weaponId}"
    const val USER_EQUIPMENT_SET_DETAIL = "user_equipment_set_detail/{setId}"

    fun armorDetailRoute(armorId: Int): String = "armor_detail/$armorId"
    fun decorationDetailRoute(decorationId: Int): String = "decoration_detail/$decorationId"
    fun itemDetailRoute(itemId: Int): String = "item_detail/$itemId"
    fun locationDetailRoute(locationId: Int): String = "location_detail/$locationId"
    fun monsterDetailRoute(monsterId: Int): String = "monster_detail/$monsterId"
    fun questDetailRoute(questId: Int): String = "quest_detail/$questId"
    fun skillTreeDetailRoute(skillTreeId: Int): String = "skill_tree_detail/$skillTreeId"
    fun weaponTreeRoute(weaponType: String): String = "weapon_tree/$weaponType"
    fun weaponDetailRoute(weaponId: Int): String = "weapon_detail/$weaponId"
    fun userEquipmentSetDetailRoute(setId: Int): String = "user_equipment_set_detail/$setId"

}
