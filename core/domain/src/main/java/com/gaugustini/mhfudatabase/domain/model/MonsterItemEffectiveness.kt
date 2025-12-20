package com.gaugustini.mhfudatabase.domain.model

import com.gaugustini.mhfudatabase.domain.enums.MonsterState

/**
 * Represents the effectiveness of certain items on a monster in different states.
 *
 * @property monsterId The unique identifier of the monster.
 * @property monsterState The state of the monster (e.g., Normal, Enraged).
 * @property canUsePitfallTrap Whether a pitfall trap can be used.
 * @property canUseShockTrap Whether a shock trap can be used.
 * @property canUseFlashBomb Whether a flash bomb can be used.
 * @property canUseSonicBomb Whether a sonic bomb can be used.
 * @property canUseDungBomb Whether a dung bomb can be used.
 * @property canUseMeat Whether meat can be used.
 */
data class MonsterItemEffectiveness(
    val monsterId: Int,
    val monsterState: MonsterState,
    val canUsePitfallTrap: Boolean,
    val canUseShockTrap: Boolean,
    val canUseFlashBomb: Boolean,
    val canUseSonicBomb: Boolean,
    val canUseDungBomb: Boolean,
    val canUseMeat: Boolean,
)
