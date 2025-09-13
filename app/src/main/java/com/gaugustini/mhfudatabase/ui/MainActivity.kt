package com.gaugustini.mhfudatabase.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.ui.Modifier
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.gaugustini.mhfudatabase.ui.theme.MHFUDatabaseTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)
        setContent {
            MHFUDatabaseTheme {
                Scaffold(modifier = Modifier.Companion.fillMaxSize()) { innerPadding ->
                    Text(
                        text = "Hello Android!",
                        modifier = Modifier.Companion.padding(innerPadding)
                    )
                }
            }
        }
    }

}
