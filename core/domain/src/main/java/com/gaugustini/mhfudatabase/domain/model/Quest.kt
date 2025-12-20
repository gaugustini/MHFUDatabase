package com.gaugustini.mhfudatabase.domain.model

import com.gaugustini.mhfudatabase.domain.enums.HubType
import com.gaugustini.mhfudatabase.domain.enums.QuestGoal
import com.gaugustini.mhfudatabase.domain.enums.QuestType

/**
 * Represents a quest in the game.
 *
 * @property id The unique identifier for the quest.
 * @property name The name of the quest.
 * @property goal The objective of the quest.
 * @property client The person that created the quest.
 * @property description The description of the quest in the game.
 * @property questType The type of the quest (Normal, Key, Urgent or Special).
 * @property goalType The goal type of the quest (Gather, Hunt, Slay or Special).
 * @property hubType The hub where the quest is available (e.g., Village, Guild).
 * @property stars The star level of the quest, indicating its difficulty.
 * @property reward The monetary reward for completing the quest.
 * @property fee The fee to undertake the quest.
 * @property timeLimit The time limit for the quest in minutes.
 * @property location The location where the quest takes place.
 * @property monsters A list of monsters that appear in the quest.
 */
// TODO: Add ranks
data class Quest(
    val id: Int,
    val name: String,
    val goal: String,
    val client: String,
    val description: String,
    val questType: QuestType,
    val goalType: QuestGoal,
    val hubType: HubType,
    val stars: Int,
    val reward: Int,
    val fee: Int,
    val timeLimit: Int,
    val location: Location,
    val monsters: List<Monster>,
)
