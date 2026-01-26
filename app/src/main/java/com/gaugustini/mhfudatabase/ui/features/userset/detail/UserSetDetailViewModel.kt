package com.gaugustini.mhfudatabase.ui.features.userset.detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gaugustini.mhfudatabase.data.preferences.UserPreferences
import com.gaugustini.mhfudatabase.data.repository.ArmorRepository
import com.gaugustini.mhfudatabase.data.repository.DecorationRepository
import com.gaugustini.mhfudatabase.data.repository.UserEquipmentSetRepository
import com.gaugustini.mhfudatabase.data.repository.WeaponRepository
import com.gaugustini.mhfudatabase.domain.enums.EquipmentType
import com.gaugustini.mhfudatabase.domain.enums.Language
import com.gaugustini.mhfudatabase.domain.model.Armor
import com.gaugustini.mhfudatabase.domain.model.Decoration
import com.gaugustini.mhfudatabase.domain.model.EquipmentDecoration
import com.gaugustini.mhfudatabase.domain.model.ItemQuantity
import com.gaugustini.mhfudatabase.domain.model.Skill
import com.gaugustini.mhfudatabase.domain.model.SkillPoint
import com.gaugustini.mhfudatabase.domain.model.UserEquipmentSet
import com.gaugustini.mhfudatabase.domain.model.Weapon
import com.gaugustini.mhfudatabase.ui.features.userset.components.ArmorSelectionFilter
import com.gaugustini.mhfudatabase.ui.features.userset.components.DecorationSelectionFilter
import com.gaugustini.mhfudatabase.ui.features.userset.components.WeaponSelectionFilter
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
    val setDecorations: List<EquipmentDecoration> = emptyList(),
    val setActiveSkills: List<Skill> = emptyList(),
    val setSkillTreePoints: List<SkillPoint> = emptyList(),
    val setRequiredMaterials: List<ItemQuantity> = emptyList(),
    val openSelectionEquipment: Boolean = false,
    val selectionType: SelectionType? = null,
    val weapons: List<Weapon> = emptyList(),
    val armors: List<Armor> = emptyList(),
    val decorations: List<Decoration> = emptyList(),
    val weaponSelectionFilter: WeaponSelectionFilter = WeaponSelectionFilter(),
    val armorSelectionFilter: ArmorSelectionFilter = ArmorSelectionFilter(),
    val decorationSelectionFilter: DecorationSelectionFilter = DecorationSelectionFilter(),
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

    private var setId: Int = checkNotNull(savedStateHandle["setId"])

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
                    userEquipmentSetRepository.getEquipmentSet(setId, language.code)

                _uiState.update { state ->
                    state.copy(
                        set = equipmentSetDetails,
                        setWeapon = equipmentSetDetails.weapon,
                        setArmors = equipmentSetDetails.armors ?: emptyList(),
                        setDecorations = equipmentSetDetails.decorations ?: emptyList(),
                        setActiveSkills = equipmentSetDetails.activeSkills ?: emptyList(),
                        setSkillTreePoints = equipmentSetDetails.skills ?: emptyList(),
                        setRequiredMaterials = equipmentSetDetails.recipe ?: emptyList(),
                    )
                }
            }
        }
    }

    private fun updateSetData() {
        viewModelScope.launch {
            _uiState.value.language

            _uiState.update { state ->
                state.copy(
                    // TODO: Update set data
                )
            }
        }
    }

    private fun saveChanges() {
        viewModelScope.launch {
            val currentState = _uiState.value

            val currentUserEquipmentSet = currentState.set!!

            if (setId != 0) {
                userEquipmentSetRepository.updateEquipmentSet(currentUserEquipmentSet)
            } else {
                setId = userEquipmentSetRepository.insertNewEquipmentSet(currentUserEquipmentSet)
            }

            updateSetData()
        }
    }

    fun renameUserSet(newSetName: String) {
        _uiState.update { state ->
            state.copy(
                set = state.set?.copy(name = newSetName),
            )
        }
        saveChanges()
    }

    fun deleteUserSet() {
        viewModelScope.launch {
            userEquipmentSetRepository.deleteEquipmentSet(setId)
        }
    }

    fun openWeaponSelection() {
        viewModelScope.launch {
            val currentLanguage = _uiState.value.language

            _uiState.update { state ->
                state.copy(
                    openSelectionEquipment = true,
                    selectionType = SelectionType.WEAPON,
                    weapons = weaponRepository.getWeaponList(currentLanguage.code)
                )
            }
        }
    }

    fun openArmorSelection(armorType: EquipmentType) {
        viewModelScope.launch {
            _uiState.value.language

            _uiState.update { state ->
                state.copy(
                    openSelectionEquipment = true,
                    selectionType = SelectionType.ARMOR,
//                    armors = armorRepository.getArmorList(armorType, currentLanguage), TODO: Add armor list
                    armorSelectionFilter = ArmorSelectionFilter(armorType = armorType)
                )
            }
        }
    }

    fun openDecorationSelection(
        equipmentType: EquipmentType,
        availableSlots: Int,
    ) {
        viewModelScope.launch {
            _uiState.value.language

            _uiState.update { state ->
                state.copy(
                    openSelectionEquipment = true,
                    selectionType = SelectionType.DECORATION,
//                    decorations = decorationRepository.getDecorationList(availableSlots,currentLanguage), TODO: Add decoration list
                    decorationSelectionFilter = DecorationSelectionFilter(
                        availableSlots = availableSlots,
                        equipmentType = equipmentType,
                    ),
                )
            }
        }
    }

    fun closeSelection() {
        _uiState.update { state ->
            state.copy(
                openSelectionEquipment = false,
                selectionType = null,
                weapons = emptyList(),
                armors = emptyList(),
                decorations = emptyList(),
                weaponSelectionFilter = WeaponSelectionFilter(),
                armorSelectionFilter = ArmorSelectionFilter(),
                decorationSelectionFilter = DecorationSelectionFilter(),
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
                setWeapon = selectedWeapon,
                setDecorations = newSetDecorations,
            )
        }

        saveChanges()
    }

    fun changeArmor(armorId: Int) {
        val currentState = _uiState.value

        val selectedArmor = currentState.armors.firstOrNull { it.id == armorId } ?: return

        val changedArmorType = selectedArmor.type

        val newSetArmors = currentState.setArmors
            .filter { it.type != changedArmorType }
            .plus(selectedArmor)

        val newSetDecorations = currentState.setDecorations
            .filter { it.equipmentType != changedArmorType }

        _uiState.update { state ->
            state.copy(
                setArmors = newSetArmors,
                setDecorations = newSetDecorations,
            )
        }

        saveChanges()
    }

    fun addDecoration(decorationId: Int) {
        val currentState = _uiState.value

        val selectedDecoration = currentState.decorations.firstOrNull { it.id == decorationId } ?: return
        val selectedEquipmentType = currentState.decorationSelectionFilter.equipmentType

        val currentDecorationInSet = currentState.setDecorations.firstOrNull {
            it.decoration.id == decorationId && it.equipmentType == selectedEquipmentType
        }

        val newSetDecorations = if (currentDecorationInSet == null) {
            val newDecoration = EquipmentDecoration(
                decoration = selectedDecoration,
                equipmentType = selectedEquipmentType,
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
                setDecorations = newSetDecorations,
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
            it.decoration.id == decorationId && it.equipmentType == equipmentType
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

    fun applyWeaponFilter(filter: WeaponSelectionFilter) {
        viewModelScope.launch {
            val currentLanguage = _uiState.value.language
            var newWeaponList = weaponRepository.getWeaponList(
                language = currentLanguage.code,
            )

            if (filter.weaponType.isNotEmpty()) {
                newWeaponList = newWeaponList.filter { weapon ->
                    weapon.type in filter.weaponType
                }
            }
            if (filter.elementType.isNotEmpty()) {
                newWeaponList = newWeaponList.filter { weapon ->
                    weapon.element1 in filter.elementType || weapon.element2 in filter.elementType
                }
            }
            if (filter.numberOfSlots.isNotEmpty()) {
                newWeaponList = newWeaponList.filter { weapon ->
                    weapon.numberOfSlots in filter.numberOfSlots
                }
            }
            if (filter.rarity.isNotEmpty()) {
                newWeaponList = newWeaponList.filter { weapon ->
                    weapon.rarity in filter.rarity
                }
            }

            _uiState.update { state ->
                state.copy(
                    weapons = newWeaponList,
                    weaponSelectionFilter = filter,
                )
            }
        }
    }

    fun applyArmorFilter(filter: ArmorSelectionFilter) {
        viewModelScope.launch {
//            TODO: Add armor filter
//            val currentLanguage = _uiState.value.language
//            var newArmorList = armorRepository.getArmorListForUserEquipmentSet(
//                query = filter.name,
//                armorType = filter.armorType,
//                language = currentLanguage,
//            )
//
//            if (filter.numberOfSlots.isNotEmpty()) {
//                newArmorList = newArmorList.filter { armor ->
//                    armor.numberOfSlots in filter.numberOfSlots
//                }
//            }
//            if (filter.rarity.isNotEmpty()) {
//                newArmorList = newArmorList.filter { armor ->
//                    armor.rarity in filter.rarity
//                }
//            }
//
//            _uiState.update { state ->
//                state.copy(
//                    armors = newArmorList,
//                    armorSelectionFilter = filter,
//                )
//            }
        }
    }

    fun applyDecorationFilter(filter: DecorationSelectionFilter) {
//        TODO: Add decoration filter
//        viewModelScope.launch {
//            val currentLanguage = _uiState.value.language
//            var newDecorationList = decorationRepository.getDecorationListForUserEquipmentSet(
//                query = filter.name,
//                availableSlots = filter.availableSlots,
//                language = currentLanguage,
//            )
//
//            if (filter.numberOfSlots.isNotEmpty()) {
//                newDecorationList = newDecorationList.filter { decoration ->
//                    decoration.requiredSlots in filter.numberOfSlots
//                }
//            }
//
//            _uiState.update { state ->
//                state.copy(
//                    decorations = newDecorationList,
//                    decorationSelectionFilter = filter,
//                )
//            }
//        }
    }

}
