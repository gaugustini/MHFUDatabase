package com.gaugustini.mhfudatabase.domain.enums

/**
 * Represents the skill category.
 */
enum class SkillCategory {
    /**
     * Skills for blademaster weapons.
     */
    BLADE,

    /**
     * General combat skills.
     */
    COMBAT,

    /**
     * Felyne skills.
     */
    FELYNE,

    /**
     * Gathering skills.
     */
    GATHER,

    /**
     * Skills for gunner weapons.
     */
    GUNNER,

    /**
     * Item related skills.
     */
    ITEM,

    /**
     * Elemental resistance skills.
     */
    RESISTANCE,

    /**
     * Status related skills.
     */
    STATUS;

    companion object {

        /**
         * Converts a string value to a [SkillCategory].
         */
        fun fromString(string: String): SkillCategory {
            return when (string) {
                "BLADE" -> BLADE
                "COMBAT" -> COMBAT
                "FELYNE" -> FELYNE
                "GATHER" -> GATHER
                "GUNNER" -> GUNNER
                "ITEM" -> ITEM
                "RESISTANCE" -> RESISTANCE
                "STATUS" -> STATUS
                else -> throw IllegalArgumentException("Invalid skill category value: $string")
            }
        }

    }
}
