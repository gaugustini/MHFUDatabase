package com.gaugustini.mhfudatabase.domain.enums

/**
 * Represents the available languages of the app.
 */
enum class Language(val code: String) {
    ENGLISH("en"),
    SPANISH("es");

    companion object {
        /**
         * Returns the language from the given code, or ENGLISH if the code language is not supported.
         */
        fun fromCode(code: String): Language {
            val base = code.lowercase().substringBefore("-")
            return entries.find { it.code == base } ?: ENGLISH
        }
    }
}
