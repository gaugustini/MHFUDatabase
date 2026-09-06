package com.gaugustini.mhfudatabase.data.util

import java.text.Normalizer

/**
 * Strips diacritics and lowercases, for accent-insensitive search.
 */
fun String.normalizeForSearch(): String {
    val decomposed = Normalizer.normalize(this, Normalizer.Form.NFD)
    return decomposed.replace(Regex("\\p{Mn}+"), "").lowercase()
}
