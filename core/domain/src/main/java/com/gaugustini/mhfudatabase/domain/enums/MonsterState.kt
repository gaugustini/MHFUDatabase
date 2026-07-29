package com.gaugustini.mhfudatabase.domain.enums

/**
 * Represents the monster state.
 */
enum class MonsterState {
    /**
     * Normal state.
     */
    NORMAL,

    /**
     * Enraged state.
     */
    ENRAGED,

    /**
     * When the player has not been seen by the monster.
     */
    UNSEEN;

    companion object {

        /**
         * Converts a string value to a [MonsterState].
         */
        fun fromString(string: String): MonsterState {
            return when (string) {
                "NORMAL" -> NORMAL
                "ENRAGED" -> ENRAGED
                "UNSEEN" -> UNSEEN
                else -> throw IllegalArgumentException("Invalid monster state value: $string")
            }
        }

    }
}
