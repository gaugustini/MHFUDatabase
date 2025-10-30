package com.gaugustini.mhfudatabase.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.runtime.getValue
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        val splashScreen = installSplashScreen()
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)

        splashScreen.setKeepOnScreenCondition {
            viewModel.uiState.value.isThemeLoading
        }

        setContent {
            val uiState by viewModel.uiState.collectAsStateWithLifecycle()

            if (!uiState.isThemeLoading) {
                MHFUDatabase(
                    uiState = uiState,
                    onBetaDialogDismissed = viewModel::onBetaDialogDismissed,
                )
            }
        }
    }

}
