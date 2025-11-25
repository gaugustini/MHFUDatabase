package com.gaugustini.mhfudatabase.ui.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gaugustini.mhfudatabase.data.Language
import com.gaugustini.mhfudatabase.data.ThemeMode
import com.gaugustini.mhfudatabase.data.UserPreferences
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

data class SettingsState(
    val themeMode: ThemeMode = ThemeMode.SYSTEM,
    val language: Language = Language.ENGLISH,
)

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val userPreferences: UserPreferences,
) : ViewModel() {

    private val _uiState = MutableStateFlow(SettingsState())
    val uiState: StateFlow<SettingsState> = _uiState.asStateFlow()

    init {
        observeThemeMode()
        observeLanguage()
    }

    private fun observeThemeMode() {
        userPreferences.getThemeMode()
            .onEach { themeMode ->
                _uiState.update { state ->
                    state.copy(themeMode = themeMode)
                }
            }
            .launchIn(viewModelScope)
    }

    fun setThemeMode(themeMode: ThemeMode) {
        viewModelScope.launch {
            userPreferences.setThemeMode(themeMode)
        }
    }

    private fun observeLanguage() {
        userPreferences.getLanguage()
            .onEach { language ->
                _uiState.update { state ->
                    state.copy(language = language)
                }
            }
            .launchIn(viewModelScope)
    }

    fun setLanguage(language: Language) {
        viewModelScope.launch {
            userPreferences.setLanguage(language)
        }
    }

}
