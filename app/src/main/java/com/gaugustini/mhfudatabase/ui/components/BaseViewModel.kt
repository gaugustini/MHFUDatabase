package com.gaugustini.mhfudatabase.ui.components

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gaugustini.mhfudatabase.data.Language
import com.gaugustini.mhfudatabase.data.UserPreferences
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

abstract class BaseViewModel(
    userPreferences: UserPreferences,
) : ViewModel() {

    init {
        userPreferences.getLanguage()
            .distinctUntilChanged()
            .onEach { language ->
                onLanguageChanged(language)
            }
            .launchIn(viewModelScope)
    }

    protected abstract fun onLanguageChanged(language: Language)

}
