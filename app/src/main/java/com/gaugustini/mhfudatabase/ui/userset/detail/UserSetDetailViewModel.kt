package com.gaugustini.mhfudatabase.ui.userset.detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gaugustini.mhfudatabase.data.Language
import com.gaugustini.mhfudatabase.data.UserPreferences
import com.gaugustini.mhfudatabase.data.enums.ArmorType
import com.gaugustini.mhfudatabase.data.enums.EquipmentType
import com.gaugustini.mhfudatabase.data.model.Armor
import com.gaugustini.mhfudatabase.data.model.Decoration
import com.gaugustini.mhfudatabase.data.model.EquipmentSetDecoration
import com.gaugustini.mhfudatabase.data.model.ItemQuantity
import com.gaugustini.mhfudatabase.data.model.Skill
import com.gaugustini.mhfudatabase.data.model.SkillTreePoints
import com.gaugustini.mhfudatabase.data.model.UserEquipmentSet
import com.gaugustini.mhfudatabase.data.model.UserEquipmentSetDetails
import com.gaugustini.mhfudatabase.data.model.Weapon
import com.gaugustini.mhfudatabase.data.repository.ArmorRepository
import com.gaugustini.mhfudatabase.data.repository.DecorationRepository
import com.gaugustini.mhfudatabase.data.repository.UserEquipmentSetRepository
import com.gaugustini.mhfudatabase.data.repository.WeaponRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

data class UserSetDetailState(
    val initialTab: UserSetDetailTab = UserSetDetailTab.EQUIPMENT,
    val language: Language = Language.ENGLISH,
    val set: UserEquipmentSet? = null,
    val setWeapon: Weapon? = null,
    val setArmors: List<Armor> = emptyList(),
    val setDecorations: List<EquipmentSetDecoration> = emptyList(),
    val setActiveSkills: List<Skill> = emptyList(),
    val setSkillTreePoints: List<SkillTreePoints> = emptyList(),
    val setRequiredMaterials: List<ItemQuantity> = emptyList(),
    val openSelectionEquipment: Boolean = false,
    val selectionType: SelectionType? = null,
    val selectionEquipmentType: EquipmentType = EquipmentType.WEAPON,
    val weapons: List<Weapon> = emptyList(),
    val armors: List<Armor> = emptyList(),
    val decorations: List<Decoration> = emptyList(),
)

enum class SelectionType {
    WEAPON,
    ARMOR,
    DECORATION
}

@HiltViewModel
class UserSetDetailViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val userPreferences: UserPreferences,
    private val userEquipmentSetRepository: UserEquipmentSetRepository,
    private val weaponRepository: WeaponRepository,
    private val armorRepository: ArmorRepository,
    private val decorationRepository: DecorationRepository,
) : ViewModel() {

    private val setId: Int = checkNotNull(savedStateHandle["setId"])

    private val _uiState = MutableStateFlow(UserSetDetailState())
    val uiState: StateFlow<UserSetDetailState> = _uiState.asStateFlow()

    init {
        observeLanguage()
    }

    private fun observeLanguage() {
        userPreferences.getLanguage()
            .distinctUntilChanged()
            .onEach { language ->
                _uiState.update { state ->
                    state.copy(language = language)
                }
                loadEquipmentSetDetails(language)
            }
            .launchIn(viewModelScope)
    }

    private fun loadEquipmentSetDetails(language: Language) {
        if (setId != 0) {
            viewModelScope.launch {
                val equipmentSetDetails =
                    userEquipmentSetRepository.getEquipmentSetDetails(setId, language)

                _uiState.update { state ->
                    state.copy(
                        set = equipmentSetDetails.set,
                        setWeapon = equipmentSetDetails.weapon,
                        setArmors = equipmentSetDetails.armors,
                        setDecorations = equipmentSetDetails.decorations,
                        setActiveSkills = userEquipmentSetRepository.getActiveSkillsForSet(
                            setId,
                            language
                        ),
                        setSkillTreePoints = userEquipmentSetRepository.getSkillTreePointsInSet(
                            setId,
                            language
                        ),
                        setRequiredMaterials = userEquipmentSetRepository.getRequiredMaterialsForSet(
                            setId,
                            language
                        ),
                    )
                }
            }
        }
    }

    private fun updateSetData() {
        viewModelScope.launch {
            val currentLanguage = _uiState.value.language

            _uiState.update { state ->
                state.copy(
                    setActiveSkills = userEquipmentSetRepository.getActiveSkillsForSet(
                        setId,
                        currentLanguage
                    ),
                    setSkillTreePoints = userEquipmentSetRepository.getSkillTreePointsInSet(
                        setId,
                        currentLanguage
                    ),
                    setRequiredMaterials = userEquipmentSetRepository.getRequiredMaterialsForSet(
                        setId,
                        currentLanguage
                    ),
                )
            }
        }
    }

    private fun saveChanges() {
        viewModelScope.launch {
            val currentState = _uiState.value

            val currentUserEquipmentSet = UserEquipmentSetDetails(
                set = currentState.set ?: UserEquipmentSet(0, ""),
                weapon = currentState.setWeapon,
                armors = currentState.setArmors,
                decorations = currentState.setDecorations,
            )

            if (setId != 0) {
                userEquipmentSetRepository.updateSet(currentUserEquipmentSet)
            } else {
                userEquipmentSetRepository.insertNewSet(currentUserEquipmentSet)
            }

            updateSetData()
        }
    }

    fun openWeaponSelection() {
        viewModelScope.launch {
            val currentLanguage = _uiState.value.language

            _uiState.update { state ->
                state.copy(
                    openSelectionEquipment = true,
                    selectionType = SelectionType.WEAPON,
                    weapons = weaponRepository.getWeaponListForUserEquipmentSet(currentLanguage)
                )
            }
        }
    }

    fun openArmorSelection(armorType: ArmorType) {
        viewModelScope.launch {
            val currentLanguage = _uiState.value.language

            _uiState.update { state ->
                state.copy(
                    openSelectionEquipment = true,
                    selectionType = SelectionType.ARMOR,
                    armors = armorRepository.getArmorListForUserEquipmentSet(armorType, currentLanguage)
                )
            }
        }
    }

    fun openDecorationSelection(
        equipmentType: EquipmentType,
        availableSlots: Int,
    ) {
        viewModelScope.launch {
            val currentLanguage = _uiState.value.language

            _uiState.update { state ->
                state.copy(
                    openSelectionEquipment = true,
                    selectionType = SelectionType.DECORATION,
                    selectionEquipmentType = equipmentType,
                    decorations = decorationRepository.getDecorationListForUserEquipmentSet(
                        availableSlots,
                        currentLanguage,
                    )
                )
            }
        }
    }

    fun closeSelection() {
        _uiState.update { state ->
            state.copy(
                openSelectionEquipment = false,
                selectionType = null,
            )
        }
    }

    fun changeWeapon(weaponId: Int) {
        val currentState = _uiState.value

        val selectedWeapon = currentState.weapons.firstOrNull { it.id == weaponId } ?: return

        val newSetDecorations = currentState.setDecorations
            .filter { it.equipmentType != EquipmentType.WEAPON }

        _uiState.update { state ->
            state.copy(
                openSelectionEquipment = false,
                selectionType = null,
                setWeapon = selectedWeapon,
                setDecorations = newSetDecorations,
                weapons = emptyList(),
            )
        }

        saveChanges()
    }

    fun changeArmor(armorId: Int) {
        val currentState = _uiState.value

        val selectedArmor = currentState.armors.firstOrNull { it.id == armorId } ?: return

        val changedArmorType = selectedArmor.type
        val changedEquipmentType = when (changedArmorType) {
            ArmorType.HEAD -> EquipmentType.ARMOR_HEAD
            ArmorType.CHEST -> EquipmentType.ARMOR_CHEST
            ArmorType.ARMS -> EquipmentType.ARMOR_ARMS
            ArmorType.WAIST -> EquipmentType.ARMOR_WAIST
            ArmorType.LEGS -> EquipmentType.ARMOR_LEGS
        }

        val newSetArmors = currentState.setArmors
            .filter { it.type != changedArmorType }
            .plus(selectedArmor)

        val newSetDecorations = currentState.setDecorations
            .filter { it.equipmentType != changedEquipmentType }

        _uiState.update { state ->
            state.copy(
                openSelectionEquipment = false,
                selectionType = null,
                setArmors = newSetArmors,
                setDecorations = newSetDecorations,
                armors = emptyList(),
            )
        }

        saveChanges()
    }

    fun addDecoration(decorationId: Int) {
        val currentState = _uiState.value

        val selectedDecoration = currentState.decorations.firstOrNull { it.id == decorationId } ?: return

        val currentDecorationInSet = currentState.setDecorations.firstOrNull {
            it.decorationId == decorationId && it.equipmentType == currentState.selectionEquipmentType
        }

        val newSetDecorations = if (currentDecorationInSet == null) {
            val newDecoration = EquipmentSetDecoration(
                setId = currentState.set?.id ?: 0,
                decorationId = selectedDecoration.id,
                name = selectedDecoration.name,
                requiredSlots = selectedDecoration.requiredSlots,
                decorationColor = selectedDecoration.iconColor,
                equipmentType = currentState.selectionEquipmentType,
                quantity = 1,
            )
            currentState.setDecorations + newDecoration
        } else {
            currentState.setDecorations.map { decoration ->
                if (decoration == currentDecorationInSet) {
                    decoration.copy(quantity = decoration.quantity + 1)
                } else {
                    decoration
                }
            }
        }

        _uiState.update {
            it.copy(
                openSelectionEquipment = false,
                selectionType = null,
                setDecorations = newSetDecorations,
                decorations = emptyList(),
            )
        }

        saveChanges()
    }

    fun removeDecoration(
        decorationId: Int,
        equipmentType: EquipmentType,
    ) {
        val currentState = _uiState.value

        val currentDecorationInSet = _uiState.value.setDecorations.first {
            it.decorationId == decorationId && it.equipmentType == equipmentType
        }

        val newSetDecorations = if (currentDecorationInSet.quantity == 1) {
            currentState.setDecorations - currentDecorationInSet
        } else {
            currentState.setDecorations.map { decoration ->
                if (decoration == currentDecorationInSet) {
                    decoration.copy(quantity = decoration.quantity - 1)
                } else {
                    decoration
                }
            }
        }

        _uiState.update { state ->
            state.copy(
                setDecorations = newSetDecorations,
            )
        }

        saveChanges()
    }

}
