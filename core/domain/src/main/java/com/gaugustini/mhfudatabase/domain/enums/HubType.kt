package com.gaugustini.mhfudatabase.domain.enums

/**
 * Represents the quest hub.
 */
enum class HubType {
    /**
     * Village quests.
     */
    VILLAGE,

    /**
     * Guild quests.
     */
    GUILD;

    companion object {

        /**
         * Converts a string value to a [HubType].
         */
        fun fromString(string: String): HubType {
            return when (string) {
                "VILLAGE" -> VILLAGE
                "GUILD" -> GUILD
                else -> throw IllegalArgumentException("Invalid quest hub value: $string")
            }
        }

    }
}
