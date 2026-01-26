package com.gaugustini.mhfudatabase.util

import androidx.compose.ui.graphics.Color
import com.gaugustini.mhfudatabase.domain.enums.ItemIconColor
import com.gaugustini.mhfudatabase.domain.enums.QuestGoal

object MHFUColors {

    private fun getRarityColor(rarity: Int): Color {
        return when (rarity) {
            1 -> Color(0xFFFFFFFF)
            2 -> Color(0xFFFFFFFF)
            3 -> Color(0xFFFFFFFF)
            4 -> Color(0xFF73CB8D)
            5 -> Color(0xFFED93A4)
            6 -> Color(0xFF96B5FD)
            7 -> Color(0xFFFF985D)
            8 -> Color(0xFFFF5D5D)
            9 -> Color(0xFFFFD35D)
            10 -> Color(0xFFAC5CC0)
            else -> Color(0xFFFFFFFF)
        }
    }

    fun getArmorColor(rarity: Int): Color {
        return getRarityColor(rarity)
    }

    fun getArmorSetColor(rarity: Int): Color {
        return getRarityColor(rarity)
    }

    fun getItemColor(color: ItemIconColor): Color {
        return when (color) {
            ItemIconColor.BLUE -> Color(0xFF96B5FD)
            ItemIconColor.GRAY -> Color(0xFFA0A0A0)
            ItemIconColor.GREEN -> Color(0xFF73CB8D)
            ItemIconColor.ORANGE -> Color(0xFFFF985D)
            ItemIconColor.PINK -> Color(0xFFED93A4)
            ItemIconColor.PURPLE -> Color(0xFFB895C6)
            ItemIconColor.RED -> Color(0xFFFF5D5D)
            ItemIconColor.SKY -> Color(0xFF9BDFF0)
            ItemIconColor.WHITE -> Color(0xFFFFFFFF)
            ItemIconColor.YELLOW -> Color(0xFFFFD35D)
        }
    }

    fun getQuestColor(goalType: QuestGoal): Color {
        return when (goalType) {
            QuestGoal.GATHER -> Color(0xFF00FF00)
            QuestGoal.HUNT -> Color(0xFFFFFFFF)
            QuestGoal.SLAY -> Color(0xFFFF0000)
            QuestGoal.SPECIAL -> Color(0xFFFF00FF)
        }
    }

    fun getWeaponColor(rarity: Int): Color {
        return getRarityColor(rarity)
    }

    object Sharpness {
        val Red = Color(0xFFC60839)
        val Orange = Color(0xFFEF5218)
        val Yellow = Color(0xFFF7CE31)
        val Green = Color(0xFF5AD600)
        val Blue = Color(0xFF316BEF)
        val White = Color(0xFFF7F7F7)
        val Purple = Color(0xFFF700F7)
        val None = Color(0xFF222222)
    }

    object SongNotes {
        val A = Color(0xFF6CC8FE)
        val B = Color(0xFF7175FD)
        val G = Color(0xFF7BE61F)
        val P = Color(0xFFE176EE)
        val R = Color(0xFFFD3B1F)
        val W = Color(0xFFFFFFFF)
        val Y = Color(0xFFFFEB2B)
    }

}
