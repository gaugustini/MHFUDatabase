package com.gaugustini.mhfudatabase.ui

import androidx.appcompat.app.AppCompatDelegate
import androidx.core.os.LocaleListCompat
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gaugustini.mhfudatabase.BuildConfig
import com.gaugustini.mhfudatabase.data.preferences.AppPreferences
import com.gaugustini.mhfudatabase.data.preferences.UserPreferences
import com.gaugustini.mhfudatabase.domain.enums.ThemeMode
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

data class MainUiState(
    val isLoading: Boolean = true,
    val themeMode: ThemeMode? = null,
    val showBetaDialog: Boolean = false,
    val showWhatsNew: Boolean = false,
)

@HiltViewModel
class MainViewModel @Inject constructor(
    private val appPreferences: AppPreferences,
    private val userPreferences: UserPreferences
) : ViewModel() {

    private val _uiState = MutableStateFlow(MainUiState())
    val uiState: StateFlow<MainUiState> = _uiState.asStateFlow()

    init {
        observeTheme()
        observeLanguage()
        checkFirstLaunch()
        checkAppUpdate()
    }

    private fun observeTheme() {
        userPreferences.getThemeMode()
            .distinctUntilChanged()
            .onEach { themeMode ->
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        themeMode = themeMode
                    )
                }
            }
            .launchIn(viewModelScope)
    }

    private fun observeLanguage() {
        userPreferences.getLanguage()
            .distinctUntilChanged()
            .onEach { language ->
                val currentLocale = AppCompatDelegate.getApplicationLocales()
                val newLocale = LocaleListCompat.forLanguageTags(language.code)

                if (newLocale != currentLocale) {
                    AppCompatDelegate.setApplicationLocales(newLocale)
                }
            }
            .launchIn(viewModelScope)
    }

    private fun checkFirstLaunch() {
        viewModelScope.launch {
            if (appPreferences.isFirstLaunch()) {
                _uiState.update { state ->
                    state.copy(showBetaDialog = true)
                }
                appPreferences.setFirstLaunchDone()
            }
        }
    }

    private fun checkAppUpdate() {
        viewModelScope.launch {
            val currentVersion = BuildConfig.VERSION_CODE
            val lastVersion = appPreferences.getLastAppVersion()

            // This avoid showing the dialog on the first launch
            if (lastVersion == -1) {
                appPreferences.setLastAppVersion(currentVersion)
                return@launch
            }

            if (currentVersion > lastVersion) {
                _uiState.update { state ->
                    state.copy(showWhatsNew = true)
                }

                appPreferences.setLastAppVersion(currentVersion)
            }
        }
    }

    fun onBetaDialogDismissed() {
        _uiState.update { state ->
            state.copy(showBetaDialog = false)
        }
    }

    fun onWhatsNewDialogDismissed() {
        _uiState.update { state ->
            state.copy(showWhatsNew = false)
        }
    }

}
