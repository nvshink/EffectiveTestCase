package com.nvshink.effectivetestcase.ui

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.movableContentOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.nvshink.effectivetestcase.data.model.Profile
import com.nvshink.effectivetestcase.ui.components.NavigationBarLayout
import com.nvshink.effectivetestcase.ui.event.OnboardingEvent
import com.nvshink.effectivetestcase.ui.event.ProfileEvent
import com.nvshink.effectivetestcase.ui.screen.favorites.FavoritesScreen
import com.nvshink.effectivetestcase.ui.screen.home.HomeScreen
import com.nvshink.effectivetestcase.ui.screen.login.LogInScreen
import com.nvshink.effectivetestcase.ui.screen.onboarding.OnboardingScreen
import com.nvshink.effectivetestcase.ui.screen.profile.ProfileScreen
import com.nvshink.effectivetestcase.ui.utils.Destinations
import com.nvshink.effectivetestcase.ui.utils.FavoritesScreenRoute
import com.nvshink.effectivetestcase.ui.utils.HomeScreenRoute
import com.nvshink.effectivetestcase.ui.utils.ProfileScreenRoute
import com.nvshink.effectivetestcase.ui.viewModel.CourseViewModel
import com.nvshink.effectivetestcase.ui.viewModel.OnboardingViewModel
import com.nvshink.effectivetestcase.ui.viewModel.ProfileViewModel

@Composable
fun App(){
    val profileViewModel: ProfileViewModel = hiltViewModel()
    val profileUIState = profileViewModel.uiState.collectAsState().value
    val navController = rememberNavController()
    val navHost = remember {
        movableContentOf<PaddingValues> { innerPadding ->
            NavHost(
                navController = navController,
                startDestination = Destinations.getDefaultTopLevelRoute().route
            ) {
                composable<HomeScreenRoute> {
                    val courseViewModel: CourseViewModel  = hiltViewModel()
                    val courseUIState = courseViewModel.uiState.collectAsState().value
                    HomeScreen(innerPadding = innerPadding, courseUIState = courseUIState, onEvent = courseViewModel::onEvent)
                }
                composable<FavoritesScreenRoute> {
                    val courseViewModel: CourseViewModel  = hiltViewModel()
                    val courseUIState = courseViewModel.uiState.collectAsState().value
                    FavoritesScreen(innerPadding = innerPadding, courseUIState = courseUIState, onEvent = courseViewModel::onEvent)
                }
                composable<ProfileScreenRoute> {
                    ProfileScreen(innerPadding = innerPadding, profileUIState = profileUIState, onEvent = profileViewModel::onEvent)
                }
            }
        }
    }

    val onboardingViewModel: OnboardingViewModel = hiltViewModel()
    val onboardingShown: Boolean = onboardingViewModel.onboardingShown.collectAsState().value
    val onboardingUiState = onboardingViewModel.uiState.collectAsState().value
    val onboardingEvent = onboardingViewModel::onEvent
    // TODO(Fix blinking onboarding screen or anther app screen then stat app)
    if (!onboardingShown) {
        OnboardingScreen(
            uiState = onboardingUiState,
            onEvent = onboardingEvent,
            onContinueClicked = { onboardingEvent(OnboardingEvent.hideOnboarding) })
    } else {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentDestination = navBackStackEntry?.destination

        if (profileUIState.isAuthorized) {
            NavigationBarLayout(
                modifier = Modifier,
                currentDestination = currentDestination,
                onMenuItemSelected = {
                    navController.navigate(route = it) {
                        popUpTo(navController.graph.findStartDestination().id) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                }
            ) { innerPadding ->
                navHost(innerPadding)
            }
        } else {
            val profileOnEvent = profileViewModel::onEvent
            LogInScreen(
                uiState = profileUIState, onEvent = profileOnEvent, onLogInClick = {
                    profileOnEvent(ProfileEvent.LogIn)
                })
        }
    }
}