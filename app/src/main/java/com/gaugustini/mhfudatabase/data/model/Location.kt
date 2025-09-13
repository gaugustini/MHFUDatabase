package com.gaugustini.mhfudatabase.data.model

data class Location(
    val id: Int,
    val name: String,
)

data class LocationDetails(
    val location: Location,
    val items: List<ItemLocation>,
)
