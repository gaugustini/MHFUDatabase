package com.gaugustini.mhfudatabase.domain.enums

/**
 * Represents the item icon color.
 */
enum class ItemIconColor {
    BLUE,
    GRAY,
    GREEN,
    ORANGE,
    PINK,
    PURPLE,
    RED,
    SKY,
    WHITE,
    YELLOW;

    companion object {

        /**
         * Converts a string value to an [ItemIconColor].
         */
        fun fromString(string: String): ItemIconColor {
            return when (string) {
                "BLUE" -> BLUE
                "GRAY" -> GRAY
                "GREEN" -> GREEN
                "ORANGE" -> ORANGE
                "PINK" -> PINK
                "PURPLE" -> PURPLE
                "RED" -> RED
                "SKY" -> SKY
                "WHITE" -> WHITE
                "YELLOW" -> YELLOW
                else -> throw IllegalArgumentException("Invalid item icon color value: $string")
            }
        }

    }
}
