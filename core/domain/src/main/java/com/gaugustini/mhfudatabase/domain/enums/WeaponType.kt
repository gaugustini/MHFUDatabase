package com.gaugustini.mhfudatabase.domain.enums

/**
 * Represents the weapon type.
 */
enum class WeaponType {
    /**
     * Great Sword
     */
    GREAT_SWORD,

    /**
     * Long Sword
     */
    LONG_SWORD,

    /**
     * Sword and Shield
     */
    SWORD_AND_SHIELD,

    /**
     * Dual Blades
     */
    DUAL_BLADES,

    /**
     * Hammer
     */
    HAMMER,

    /**
     * Hunting Horn
     */
    HUNTING_HORN,

    /**
     * Lance
     */
    LANCE,

    /**
     * Gunlance
     */
    GUNLANCE,

    /**
     * Light Bowgun
     */
    LIGHT_BOWGUN,

    /**
     * Heavy Bowgun
     */
    HEAVY_BOWGUN,

    /**
     * Bow
     */
    BOW;

    companion object {

        /**
         * Converts a string value to a [WeaponType].
         */
        fun fromString(string: String): WeaponType {
            return when (string) {
                "GREAT_SWORD" -> GREAT_SWORD
                "LONG_SWORD" -> LONG_SWORD
                "SWORD_AND_SHIELD" -> SWORD_AND_SHIELD
                "DUAL_BLADES" -> DUAL_BLADES
                "HAMMER" -> HAMMER
                "HUNTING_HORN" -> HUNTING_HORN
                "LANCE" -> LANCE
                "GUNLANCE" -> GUNLANCE
                "LIGHT_BOWGUN" -> LIGHT_BOWGUN
                "HEAVY_BOWGUN" -> HEAVY_BOWGUN
                "BOW" -> BOW
                else -> throw IllegalArgumentException("Invalid weapon type value: $string")
            }
        }

        /**
         * Returns a list of related weapon types.
         * For example, Great Sword and Long Sword are related because they share the same weapon tree.
         */
        fun getRelatedWeapons(weaponType: WeaponType): List<WeaponType> {
            return when (weaponType) {
                GREAT_SWORD, LONG_SWORD -> listOf(GREAT_SWORD, LONG_SWORD)
                SWORD_AND_SHIELD, DUAL_BLADES -> listOf(SWORD_AND_SHIELD, DUAL_BLADES)
                HAMMER, HUNTING_HORN -> listOf(HAMMER, HUNTING_HORN)
                LANCE, GUNLANCE -> listOf(LANCE, GUNLANCE)
                LIGHT_BOWGUN -> listOf(LIGHT_BOWGUN)
                HEAVY_BOWGUN -> listOf(HEAVY_BOWGUN)
                BOW -> listOf(BOW)
            }
        }

    }
}
