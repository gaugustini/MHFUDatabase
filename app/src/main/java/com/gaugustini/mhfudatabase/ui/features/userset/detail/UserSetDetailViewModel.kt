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
import com.gaugustini.mhfudatabase.domain.filter.ArmorFilter
import com.gaugustini.mhfudatabase.domain.filter.DecorationFilter
import com.gaugustini.mhfudatabase.domain.filter.WeaponFilter
import com.gaugustini.mhfudatabase.domain.model.Armor
import com.gaugustini.mhfudatabase.domain.model.Decoration
import com.gaugustini.mhfudatabase.domain.model.EquipmentDecoration
import com.gaugustini.mhfudatabase.domain.model.UserEquipmentSet
import com.gaugustini.mhfudatabase.domain.model.Weapon
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
    val equipmentSet: UserEquipmentSet = UserEquipmentSet(),

    val openSelectionEquipment: Boolean = false,
    val selectionType: SelectionType? = null,
    val selectedEquipmentType: EquipmentType? = null,
    val maxAvailableSlots: Int = 3,

    val weapons: List<Weapon> = emptyList(),
    val armors: List<Armor> = emptyList(),
    val decorations: List<Decoration> = emptyList(),

    val weaponFilter: WeaponFilter = WeaponFilter(),
    val armorFilter: ArmorFilter = ArmorFilter(),
    val decorationFilter: DecorationFilter = DecorationFilter(),
)

enum class SelectionType {
    WEAPON,
    ARMOR,
    DECORATION;
}

sealed interface UserSetEvent {
    data class Rename(val name: String) : UserSetEvent
    object Delete : UserSetEvent
    data class ChangeWeapon(val weaponId: Int) : UserSetEvent
    data class ChangeArmor(val armorId: Int) : UserSetEvent
    data class AddDecoration(val decorationId: Int) : UserSetEvent
    data class RemoveDecoration(val decorationId: Int, val equipmentType: EquipmentType) : UserSetEvent
    data class OpenSelection(
        val type: SelectionType,
        val equipmentType: EquipmentType? = null,
        val availableSlots: Int? = null
    ) : UserSetEvent

    object CloseSelection : UserSetEvent
    data class ApplyWeaponFilter(val filter: WeaponFilter) : UserSetEvent
    data class ApplyArmorFilter(val filter: ArmorFilter) : UserSetEvent
    data class ApplyDecorationFilter(val filter: DecorationFilter) : UserSetEvent
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
                _uiState.update { state ->
                    state.copy(
                        equipmentSet = userEquipmentSetRepository.getEquipmentSet(setId, language.code),
                    )
                }
            }
        }
    }

    private fun saveChanges(equipmentSet: UserEquipmentSet) {
        viewModelScope.launch {
            if (setId == 0) {
                val newId = userEquipmentSetRepository.insertNewEquipmentSet(equipmentSet)
                setId = newId
            } else {
                userEquipmentSetRepository.updateEquipmentSet(equipmentSet)
            }

            loadEquipmentSetDetails(_uiState.value.language)
        }
    }

    fun onEvent(event: UserSetEvent) {
        when (event) {
            is UserSetEvent.Rename -> renameUserSet(event.name)
            is UserSetEvent.ChangeWeapon -> changeWeapon(event.weaponId)
            is UserSetEvent.ChangeArmor -> changeArmor(event.armorId)
            is UserSetEvent.AddDecoration -> addDecoration(event.decorationId)
            is UserSetEvent.RemoveDecoration -> removeDecoration(event.decorationId, event.equipmentType)
            is UserSetEvent.Delete -> deleteUserSet()
            is UserSetEvent.OpenSelection -> openEquipmentSelection(event)
            is UserSetEvent.CloseSelection -> closeEquipmentSelection()
            is UserSetEvent.ApplyWeaponFilter -> applyFilter(weaponFilter = event.filter)
            is UserSetEvent.ApplyArmorFilter -> applyFilter(armorFilter = event.filter)
            is UserSetEvent.ApplyDecorationFilter -> applyFilter(decorationFilter = event.filter)
        }
    }

    private fun renameUserSet(newSetName: String) {
        val newEquipmentSet = _uiState.value.equipmentSet.copy(name = newSetName)
        saveChanges(newEquipmentSet)
    }

    private fun changeWeapon(weaponId: Int) {
        val currentState = _uiState.value
        val selectedWeapon = currentState.weapons.find { it.id == weaponId } ?: return
        val newEquipmentSet = currentState.equipmentSet.changeWeapon(selectedWeapon)

        saveChanges(newEquipmentSet)
    }

    private fun changeArmor(armorId: Int) {
        val currentState = _uiState.value
        val selectedArmor = currentState.armors.find { it.id == armorId } ?: return
        val newEquipmentSet = currentState.equipmentSet.changeArmor(selectedArmor)

        saveChanges(newEquipmentSet)
    }

    private fun addDecoration(decorationId: Int) {
        val currentState = _uiState.value
        val selectedDecoration = currentState.decorations.find { it.id == decorationId } ?: return
        val equipmentType = currentState.selectedEquipmentType ?: return
        val newEquipmentSet = currentState.equipmentSet.addDecoration(
            newDecoration = EquipmentDecoration(equipmentType, selectedDecoration, 1),
        )

        saveChanges(newEquipmentSet)
    }

    private fun removeDecoration(decorationId: Int, equipmentType: EquipmentType) {
        val currentState = _uiState.value
        val newEquipmentSet = currentState.equipmentSet.removeDecoration(decorationId, equipmentType)

        saveChanges(newEquipmentSet)
    }

    private fun deleteUserSet() {
        viewModelScope.launch {
            userEquipmentSetRepository.deleteEquipmentSet(setId)
        }
    }

    private fun openEquipmentSelection(event: UserSetEvent.OpenSelection) {
        viewModelScope.launch {
            val currentLanguage = _uiState.value.language.code

            _uiState.update { state ->
                state.copy(
                    openSelectionEquipment = true,
                    selectionType = event.type,
                    weapons = emptyList(),
                    armors = emptyList(),
                    decorations = emptyList(),
                )
            }

            when (event.type) {
                SelectionType.WEAPON -> {
                    _uiState.update { state ->
                        state.copy(
                            weapons = weaponRepository.getWeaponList(currentLanguage),
                        )
                    }
                }

                SelectionType.ARMOR -> {
                    val equipmentType = event.equipmentType ?: return@launch
                    val filter = ArmorFilter(type = equipmentType)
                    _uiState.update { state ->
                        state.copy(
                            armors = armorRepository.getArmorList(currentLanguage, filter),
                            armorFilter = filter,
                        )
                    }
                }

                SelectionType.DECORATION -> {
                    val equipmentType = event.equipmentType ?: return@launch
                    val slots = event.availableSlots ?: return@launch
                    val filter = DecorationFilter(
                        numberOfSlots = listOf(1..slots).flatten(),
                    )
                    _uiState.update { state ->
                        state.copy(
                            selectedEquipmentType = equipmentType,
                            maxAvailableSlots = slots,
                            decorations = decorationRepository.getDecorationList(
                                currentLanguage,
                                filter
                            ),
                            decorationFilter = DecorationFilter(),
                        )
                    }
                }
            }
        }
    }

    private fun closeEquipmentSelection() {
        _uiState.update { state ->
            state.copy(
                openSelectionEquipment = false,
                selectionType = null,
                selectedEquipmentType = null,
                maxAvailableSlots = 3,
                weapons = emptyList(),
                armors = emptyList(),
                decorations = emptyList(),
                weaponFilter = WeaponFilter(),
                armorFilter = ArmorFilter(),
                decorationFilter = DecorationFilter(),
            )
        }
    }

    private fun applyFilter(
        weaponFilter: WeaponFilter? = null,
        armorFilter: ArmorFilter? = null,
        decorationFilter: DecorationFilter? = null
    ) {
        viewModelScope.launch {
            val currentLanguage = _uiState.value.language.code

            _uiState.update { state ->
                when {
                    weaponFilter != null -> {
                        state.copy(
                            weapons = weaponRepository.getWeaponList(currentLanguage, weaponFilter),
                            weaponFilter = weaponFilter,
                        )
                    }

                    armorFilter != null -> {
                        state.copy(
                            armors = armorRepository.getArmorList(currentLanguage, armorFilter),
                            armorFilter = armorFilter,
                        )
                    }

                    decorationFilter != null -> {
                        state.copy(
                            decorations = decorationRepository.getDecorationList(
                                currentLanguage,
                                decorationFilter
                            ),
                            decorationFilter = decorationFilter,
                        )
                    }

                    else -> state
                }
            }
        }
    }

}
