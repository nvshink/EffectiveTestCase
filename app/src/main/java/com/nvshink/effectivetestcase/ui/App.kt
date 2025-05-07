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
import com.nvshink.effectivetestcase.ui.components.NavigationBarLayout
import com.nvshink.effectivetestcase.ui.event.OnboardingEvent
import com.nvshink.effectivetestcase.ui.event.ProfileEvent
import com.nvshink.effectivetestcase.ui.screen.favorites.FavoritesScreen
import com.nvshink.effectivetestcase.ui.screen.home.HomeScreen
import com.nvshink.effectivetestcase.ui.screen.login.LogInScreen
import com.nvshink.effectivetestcase.ui.screen.onboarding.OnboardingScreen
import com.nvshink.effectivetestcase.ui.screen.profile.ProfileScreen
import com.nvshink.effectivetestcase.ui.states.OnboardingUIState
import com.nvshink.effectivetestcase.ui.states.ProfileUIState
import com.nvshink.effectivetestcase.ui.utils.Destinations
import com.nvshink.effectivetestcase.ui.utils.FavoritesScreenRoute
import com.nvshink.effectivetestcase.ui.utils.HomeScreenRoute
import com.nvshink.effectivetestcase.ui.utils.ProfileScreenRoute
import com.nvshink.effectivetestcase.ui.viewModel.CourseViewModel
import com.nvshink.effectivetestcase.ui.viewModel.OnboardingViewModel
import com.nvshink.effectivetestcase.ui.viewModel.ProfileViewModel

@Composable
fun App(onboardingViewModel: OnboardingViewModel, onboardingUiState: OnboardingUIState, profileViewModel: ProfileViewModel, profileUIState: ProfileUIState){
    val onboardingEvent = onboardingViewModel::onEvent
    if (onboardingUiState.isShow ?: false) {
        OnboardingScreen(
            uiState = onboardingUiState,
            onEvent = onboardingEvent,
            onContinueClicked = { onboardingEvent(OnboardingEvent.HideOnboarding) })
    } else {
        if (profileUIState.isAuthorized ?: false) {
            val navController = rememberNavController()
            val navHost = remember {
                movableContentOf<PaddingValues> { innerPadding ->
                    val courseViewModel: CourseViewModel  = hiltViewModel()
                    val courseUIState = courseViewModel.uiState.collectAsState().value
                    val profileViewModelA: ProfileViewModel  = hiltViewModel()
                    val profileUIStateA = profileViewModelA.uiState.collectAsState().value
                    NavHost(
                        navController = navController,
                        startDestination = Destinations.getDefaultTopLevelRoute().route
                    ) {
                        composable<HomeScreenRoute> {
                            HomeScreen(innerPadding = innerPadding, courseUIState = courseUIState, onEvent = courseViewModel::onEvent)
                        }
                        composable<FavoritesScreenRoute> {
                            FavoritesScreen(innerPadding = innerPadding, courseUIState = courseUIState, onEvent = courseViewModel::onEvent)
                        }
                        composable<ProfileScreenRoute> {
                            ProfileScreen(innerPadding = innerPadding, profileUIState = profileUIStateA, onEvent = profileViewModelA::onEvent)
                        }
                    }
                }
            }
            val navBackStackEntry by navController.currentBackStackEntryAsState()
            val currentDestination = navBackStackEntry?.destination
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