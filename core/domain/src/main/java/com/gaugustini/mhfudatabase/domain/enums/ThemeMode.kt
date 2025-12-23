package com.gaugustini.mhfudatabase.domain.enums

/**
 * Represents the theme of the app.
 */
enum class ThemeMode(val value: Int) {
    SYSTEM(0),
    LIGHT(1),
    DARK(2);

    companion object {
        /**
         * Returns the theme mode from the given value, or SYSTEM as default.
         */
        fun fromValue(value: Int): ThemeMode = entries.find { it.value == value } ?: SYSTEM
    }
}
