package com.nvshink.effectivetestcase.ui.utils

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.nvshink.effectivetestcase.R
import kotlinx.serialization.Serializable

object Destinations {
    data class TopLevelRoute<T : Any>(@StringRes val name: Int, val route: T, @DrawableRes val icon: Int)

    private val topLevelRoutes = listOf(
        TopLevelRoute(
            name = R.string.destination_home_name,
            route = HomeScreenRoute,
            icon = R.drawable.ic_house
        ),
        TopLevelRoute(
            name = R.string.destination_favorites_name,
            route = FavoritesScreenRoute,
            icon = R.drawable.ic_bookmark
        ),
        TopLevelRoute(
            name = R.string.destination_profile_name,
            route = ProfileScreenRoute,
            icon = R.drawable.ic_person
        )
    )

    fun getTopLevelRoutes(): List<TopLevelRoute<out Any>> {
        return topLevelRoutes
    }

    fun getDefaultTopLevelRoute(): TopLevelRoute<out Any> {
        val defaultTopLevelRoute: TopLevelRoute<out Any> = topLevelRoutes[0]
        return defaultTopLevelRoute
    }
}

@Serializable
object HomeScreenRoute

@Serializable
object FavoritesScreenRoute

@Serializable
object ProfileScreenRoute
