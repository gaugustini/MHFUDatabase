package com.gaugustini.mhfudatabase.ui

import androidx.appcompat.app.AppCompatDelegate
import androidx.core.os.LocaleListCompat
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gaugustini.mhfudatabase.data.ThemeMode
import com.gaugustini.mhfudatabase.data.UserPreferences
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

data class MainUiState(
    val isLoading: Boolean = true,
    val themeMode: ThemeMode? = null,
    val showBetaDialog: Boolean = false,
)

@HiltViewModel
class MainViewModel @Inject constructor(
    private val userPreferences: UserPreferences
) : ViewModel() {

    private val _uiState = MutableStateFlow(MainUiState())
    val uiState: StateFlow<MainUiState> = _uiState.asStateFlow()

    init {
        combine(
            userPreferences.getThemeMode(),
            userPreferences.getLanguage(),
        ) { themeMode, language ->
            Pair(themeMode, language)
        }.onEach { (themeMode, language) ->
            language.let {
                val currentLocale = AppCompatDelegate.getApplicationLocales()
                val newLocale = LocaleListCompat.forLanguageTags(it.code)

                if (newLocale != currentLocale) {
                    AppCompatDelegate.setApplicationLocales(newLocale)
                }
            }

            _uiState.update { state ->
                state.copy(
                    isLoading = false,
                    themeMode = themeMode,
                )
            }
        }.launchIn(viewModelScope)

        viewModelScope.launch {
            if (userPreferences.isFirstLaunch()) {
                _uiState.update { state ->
                    state.copy(showBetaDialog = true)
                }
                userPreferences.setFirstLaunchDone()
            }
        }
    }

    fun onBetaDialogDismissed() {
        _uiState.update { state ->
            state.copy(showBetaDialog = false)
        }
    }

}
