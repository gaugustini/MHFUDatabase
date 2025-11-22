package com.gaugustini.mhfudatabase.ui

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.runtime.getValue
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        val splashScreen = installSplashScreen()
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)

        splashScreen.setKeepOnScreenCondition {
            viewModel.uiState.value.isLoading
        }

        setContent {
            val uiState by viewModel.uiState.collectAsStateWithLifecycle()

            if (!uiState.isLoading) {
                MHFUDatabase(
                    uiState = uiState,
                    onBetaDialogDismissed = viewModel::onBetaDialogDismissed,
                    onWhatsNewDialogDismissed = viewModel::onWhatsNewDialogDismissed,
                )
            }
        }
    }

}
