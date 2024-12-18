package com.t8rin.animation.samples

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.t8rin.animation.samples.ui.App
import com.t8rin.animation.samples.ui.components.theme.AnimationSamplesTheme
import com.t8rin.animation.samples.ui.viewModel.MainViewModel

class MainActivity : ComponentActivity() {

    private val viewModel by viewModels<MainViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AnimationSamplesTheme {
                App(viewModel)
            }
        }
    }
}