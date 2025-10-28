package com.gaugustini.mhfudatabase.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.gaugustini.mhfudatabase.data.UserPreferences
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        val splashScreen = installSplashScreen()
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)

        var keepSplashScreenOn by mutableStateOf(true)
        splashScreen.setKeepOnScreenCondition { keepSplashScreenOn }

        setContent {
            val themeMode by UserPreferences.getThemeMode(this).collectAsState(initial = null)

            if (themeMode != null) {
                keepSplashScreenOn = false
                MHFUDatabase(themeMode = themeMode!!)
            }
        }
    }

}
