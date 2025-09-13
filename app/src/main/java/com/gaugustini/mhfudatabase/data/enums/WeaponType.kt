package com.gaugustini.mhfudatabase.data.enums

enum class WeaponType {
    GREAT_SWORD,
    LONG_SWORD,
    SWORD_AND_SHIELD,
    DUAL_BLADES,
    HAMMER,
    HUNTING_HORN,
    LANCE,
    GUNLANCE,
    LIGHT_BOWGUN,
    HEAVY_BOWGUN,
    BOW;

    val pairGroup: List<WeaponType>
        get() = when (this) {
            GREAT_SWORD, LONG_SWORD -> listOf(GREAT_SWORD, LONG_SWORD)
            SWORD_AND_SHIELD, DUAL_BLADES -> listOf(SWORD_AND_SHIELD, DUAL_BLADES)
            HAMMER, HUNTING_HORN -> listOf(HAMMER, HUNTING_HORN)
            LANCE, GUNLANCE -> listOf(LANCE, GUNLANCE)
            LIGHT_BOWGUN -> listOf(LIGHT_BOWGUN)
            HEAVY_BOWGUN -> listOf(HEAVY_BOWGUN)
            BOW -> listOf(BOW)
        }

    companion object {

        fun getWeaponTypeFromString(name: String): WeaponType {
            return when (name) {
                GREAT_SWORD.name -> GREAT_SWORD
                LONG_SWORD.name -> LONG_SWORD
                SWORD_AND_SHIELD.name -> SWORD_AND_SHIELD
                DUAL_BLADES.name -> DUAL_BLADES
                HAMMER.name -> HAMMER
                HUNTING_HORN.name -> HUNTING_HORN
                LANCE.name -> LANCE
                GUNLANCE.name -> GUNLANCE
                LIGHT_BOWGUN.name -> LIGHT_BOWGUN
                HEAVY_BOWGUN.name -> HEAVY_BOWGUN
                BOW.name -> BOW
                else -> GREAT_SWORD
            }
        }

    }
}
