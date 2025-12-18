package com.gaugustini.mhfudatabase.domain.enums

/**
 * Represents the hunter type for equipments.
 */
enum class HunterType {
    /**
     * Can be used by both Blademasters and Gunners.
     */
    BOTH,

    /**
     * Can only be used by Blademasters.
     */
    BLADE,

    /**
     * Can only be used by Gunners.
     */
    GUNNER;
}
