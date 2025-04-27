package com.nvshink.effectivetestcase

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.movableContentOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.nvshink.effectivetestcase.ui.App
import com.nvshink.effectivetestcase.ui.components.NavigationBarLayout
import com.nvshink.effectivetestcase.ui.event.OnboardingEvent
import com.nvshink.effectivetestcase.ui.event.ProfileEvent
import com.nvshink.effectivetestcase.ui.screen.favorites.FavoritesScreen
import com.nvshink.effectivetestcase.ui.screen.home.HomeScreen
import com.nvshink.effectivetestcase.ui.screen.onboarding.OnboardingScreen
import com.nvshink.effectivetestcase.ui.screen.profile.ProfileScreen
import com.nvshink.effectivetestcase.ui.screen.login.LogInScreen
import com.nvshink.effectivetestcase.ui.theme.EffectiveTestCaseTheme
import com.nvshink.effectivetestcase.ui.utils.Destinations
import com.nvshink.effectivetestcase.ui.utils.FavoritesScreenRoute
import com.nvshink.effectivetestcase.ui.utils.HomeScreenRoute
import com.nvshink.effectivetestcase.ui.utils.ProfileScreenRoute
import com.nvshink.effectivetestcase.ui.viewModel.OnboardingViewModel
import com.nvshink.effectivetestcase.ui.viewModel.ProfileViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            EffectiveTestCaseTheme {
                App()
            }
        }
    }
}
