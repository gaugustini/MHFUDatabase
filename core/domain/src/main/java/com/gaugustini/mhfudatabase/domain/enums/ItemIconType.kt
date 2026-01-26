package com.gaugustini.mhfudatabase.domain.enums

/**
 * Represents the item icon type.
 */
enum class ItemIconType {
    ARMOR_STONE,
    BAG,
    BAIT,
    BALL,
    BARREL,
    BBQ_SPLIT,
    BINOCULARS,
    BOMB,
    BONE,
    BOOK,
    BOOMERANG,
    BOTTLE,
    BUGNET,
    CARAPACEON_SHELL,
    COIN,
    DUNG,
    EGG,
    EMPTY_BOTTLE,
    FANG,
    FISH,
    FLUTE,
    HERB,
    HONEY,
    HUSK,
    HEAVENLY_SCALE,
    INSECT,
    JEWEL,
    KNIFE,
    LIQUID,
    MAP,
    MEAT,
    MONSTER,
    MUSHROOM,
    ORE,
    PELT,
    PICKAXE,
    SCALE,
    SEED,
    SHELL,
    SMOKE,
    TICKET,
    TOOL,
    TRAP,
    UNKNOWN,
    WEB,
    WHETSTONE;

    companion object {

        /**
         * Converts a string value to an [ItemIconType].
         */
        fun fromString(string: String): ItemIconType {
            return when (string) {
                "ARMOR_STONE" -> ARMOR_STONE
                "BAG" -> BAG
                "BAIT" -> BAIT
                "BALL" -> BALL
                "BARREL" -> BARREL
                "BBQ_SPLIT" -> BBQ_SPLIT
                "BINOCULARS" -> BINOCULARS
                "BOMB" -> BOMB
                "BONE" -> BONE
                "BOOK" -> BOOK
                "BOOMERANG" -> BOOMERANG
                "BOTTLE" -> BOTTLE
                "BUGNET" -> BUGNET
                "CARAPACEON_SHELL" -> CARAPACEON_SHELL
                "COIN" -> COIN
                "DUNG" -> DUNG
                "EGG" -> EGG
                "EMPTY_BOTTLE" -> EMPTY_BOTTLE
                "FANG" -> FANG
                "FISH" -> FISH
                "FLUTE" -> FLUTE
                "HERB" -> HERB
                "HONEY" -> HONEY
                "HUSK" -> HUSK
                "HEAVENLY_SCALE" -> HEAVENLY_SCALE
                "INSECT" -> INSECT
                "JEWEL" -> JEWEL
                "KNIFE" -> KNIFE
                "LIQUID" -> LIQUID
                "MAP" -> MAP
                "MEAT" -> MEAT
                "MONSTER" -> MONSTER
                "MUSHROOM" -> MUSHROOM
                "ORE" -> ORE
                "PELT" -> PELT
                "PICKAXE" -> PICKAXE
                "SCALE" -> SCALE
                "SEED" -> SEED
                "SHELL" -> SHELL
                "SMOKE" -> SMOKE
                "TICKET" -> TICKET
                "TOOL" -> TOOL
                "TRAP" -> TRAP
                "UNKNOWN" -> UNKNOWN
                "WEB" -> WEB
                "WHETSTONE" -> WHETSTONE
                else -> throw IllegalArgumentException("Invalid item icon type value: $string")
            }
        }

    }
}
