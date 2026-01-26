package com.gaugustini.mhfudatabase.domain.enums

/**
 * Represents the character gender for equipments.
 */
enum class Gender {
    /**
     * Can be used by both genders.
     */
    BOTH,

    /**
     * Can only be used by male characters.
     */
    MALE,

    /**
     * Can only be used by female characters.
     */
    FEMALE;

    companion object {

        /**
         * Converts a string value to a [Gender].
         */
        fun fromString(string: String): Gender {
            return when (string) {
                "BOTH" -> BOTH
                "MALE" -> MALE
                "FEMALE" -> FEMALE
                else -> throw IllegalArgumentException("Invalid gender value: $string")
            }
        }

    }
}
