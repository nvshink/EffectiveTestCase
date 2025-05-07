package com.nvshink.effectivetestcase

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.collectAsState
import androidx.core.splashscreen.SplashScreen
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.datastore.preferences.preferencesDataStore
import androidx.hilt.navigation.compose.hiltViewModel
import com.nvshink.effectivetestcase.data.datastore.dataStore
import com.nvshink.effectivetestcase.ui.App
import com.nvshink.effectivetestcase.ui.theme.EffectiveTestCaseTheme
import com.nvshink.effectivetestcase.ui.viewModel.OnboardingViewModel
import com.nvshink.effectivetestcase.ui.viewModel.ProfileViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        val splashScreen = installSplashScreen()
        splashScreen.setKeepOnScreenCondition { true }
        setContent {
            EffectiveTestCaseTheme {
                val onboardingViewModel: OnboardingViewModel = hiltViewModel()
                val onboardingUiState = onboardingViewModel.uiState.collectAsState().value
                val profileViewModel: ProfileViewModel = hiltViewModel()
                val profileUIState = profileViewModel.uiState.collectAsState().value
                splashScreen.setKeepOnScreenCondition { profileUIState.isAuthorized == null }
                App(
                    onboardingViewModel = onboardingViewModel,
                    onboardingUiState = onboardingUiState,
                    profileViewModel = profileViewModel,
                    profileUIState = profileUIState
                )
            }
        }
    }
}
