package com.gaugustini.mhfudatabase.domain.enums

/**
 * Represents a group of quests in the game.
 */
enum class QuestGroup {
    VILLAGE_1,
    VILLAGE_2,
    VILLAGE_3,
    VILLAGE_4,
    VILLAGE_5,
    VILLAGE_6,
    VILLAGE_7,
    VILLAGE_8,
    VILLAGE_9,
    HR_1_1,
    HR_1_2,
    HR_1_3,
    HR_2,
    HR_3,
    HR_4,
    HR_5,
    HR_6,
    HR_7,
    HR_8,
    HR_9,
    TREASURE,
    EVENT,
    BEGINNER_BASIC,
    BEGINNER_WEAPON,
    TRAINING_BATTLE,
    TRAINING_SPECIAL,
    TRAINING_G,
    GROUP_PRACTICE,
    GROUP_CHALLENGE;

    companion object {

        /**
         * Converts a string value to a [QuestGroup].
         */
        fun fromString(string: String): QuestGroup {
            return when (string) {
                "VILLAGE_1" -> VILLAGE_1
                "VILLAGE_2" -> VILLAGE_2
                "VILLAGE_3" -> VILLAGE_3
                "VILLAGE_4" -> VILLAGE_4
                "VILLAGE_5" -> VILLAGE_5
                "VILLAGE_6" -> VILLAGE_6
                "VILLAGE_7" -> VILLAGE_7
                "VILLAGE_8" -> VILLAGE_8
                "VILLAGE_9" -> VILLAGE_9
                "HR_1_1" -> HR_1_1
                "HR_1_2" -> HR_1_2
                "HR_1_3" -> HR_1_3
                "HR_2" -> HR_2
                "HR_3" -> HR_3
                "HR_4" -> HR_4
                "HR_5" -> HR_5
                "HR_6" -> HR_6
                "HR_7" -> HR_7
                "HR_8" -> HR_8
                "HR_9" -> HR_9
                "TREASURE" -> TREASURE
                "EVENT" -> EVENT
                "BEGINNER_BASIC" -> BEGINNER_BASIC
                "BEGINNER_WEAPON" -> BEGINNER_WEAPON
                "TRAINING_BATTLE" -> TRAINING_BATTLE
                "TRAINING_SPECIAL" -> TRAINING_SPECIAL
                "TRAINING_G" -> TRAINING_G
                "GROUP_PRACTICE" -> GROUP_PRACTICE
                "GROUP_CHALLENGE" -> GROUP_CHALLENGE
                else -> throw IllegalArgumentException("Invalid quest group value: $string")
            }
        }

    }
}