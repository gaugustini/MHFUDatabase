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

    companion object {

        /**
         * Converts a string value to an [EquipmentType].
         */
        fun fromString(string: String): EquipmentType {
            return when (string) {
                "HEAD" -> ARMOR_HEAD
                "CHEST" -> ARMOR_CHEST
                "ARMS" -> ARMOR_ARMS
                "WAIST" -> ARMOR_WAIST
                "LEGS" -> ARMOR_LEGS
                "DECORATION" -> DECORATION
                "WEAPON" -> WEAPON
                else -> throw IllegalArgumentException("Invalid equipment type value: $string")
            }
        }

    }
}
