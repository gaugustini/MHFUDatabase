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
         * Returns a [WeaponType] from a string.
         */
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

        /**
         * Returns a list of related weapon types.
         * For example, Great Sword and Long Sword share the same weapon tree.
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
