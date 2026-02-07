package com.gaugustini.mhfudatabase.domain.enums

/**
 * Represents the equipment type.
 */
enum class EquipmentType {
    /**
     * Head armor piece.
     */
    ARMOR_HEAD,

    /**
     * Chest armor piece.
     */
    ARMOR_CHEST,

    /**
     * Arms armor piece.
     */
    ARMOR_ARMS,

    /**
     * Waist armor piece.
     */
    ARMOR_WAIST,

    /**
     * Legs armor piece.
     */
    ARMOR_LEGS,

    /**
     * Decoration (jewel).
     */
    DECORATION,

    /**
     * Weapon.
     */
    WEAPON;

    /**
     * Converts an [EquipmentType] to a string value.
     */
    override fun toString(): String {
        return when (this) {
            ARMOR_HEAD -> "HEAD"
            ARMOR_CHEST -> "CHEST"
            ARMOR_ARMS -> "ARMS"
            ARMOR_WAIST -> "WAIST"
            ARMOR_LEGS -> "LEGS"
            DECORATION -> "DECORATION"
            WEAPON -> "WEAPON"
        }
    }

    companion object {

        /**
         * Converts a string value to an [EquipmentType].
         */
        fun fromString(string: String): EquipmentType {
            return when (string) {
                "HEAD", "ARMOR_HEAD" -> ARMOR_HEAD
                "CHEST", "ARMOR_CHEST" -> ARMOR_CHEST
                "ARMS", "ARMOR_ARMS" -> ARMOR_ARMS
                "WAIST", "ARMOR_WAIST" -> ARMOR_WAIST
                "LEGS", "ARMOR_LEGS" -> ARMOR_LEGS
                "DECORATION" -> DECORATION
                "WEAPON" -> WEAPON
                else -> throw IllegalArgumentException("Invalid equipment type value: $string")
            }
        }

    }
}
