package com.nvshink.effectivetestcase.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemColors
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavDestination.Companion.hierarchy
import com.nvshink.effectivetestcase.ui.utils.Destinations

@Composable
fun NavigationBarLayout(
    modifier: Modifier,
    currentDestination: NavDestination?,
    onMenuItemSelected: (Any) -> Unit,
    content: @Composable (innerPadding: PaddingValues) -> Unit
) {
    val topLevelRoutes = Destinations.getTopLevelRoutes()
    Scaffold(
        modifier = modifier,
        bottomBar = {
            BottomAppBar(containerColor = MaterialTheme.colorScheme.surfaceContainer) {
                topLevelRoutes.forEach { item ->
                    NavigationBarItem(
                        label = {
                            Text(text = stringResource(item.name))
                        },
                        icon = {
                            Icon(
                                painter = painterResource(item.icon),
                                contentDescription = stringResource(item.name)
                            )
                        },
                        colors = NavigationBarItemColors(
                            selectedIconColor = MaterialTheme.colorScheme.primary,
                            selectedTextColor = MaterialTheme.colorScheme.primary,
                            selectedIndicatorColor = MaterialTheme.colorScheme.surfaceBright,
                            unselectedTextColor = MaterialTheme.colorScheme.onSurface,
                            unselectedIconColor = MaterialTheme.colorScheme.onSurface,
                            disabledIconColor = MaterialTheme.colorScheme.outline,
                            disabledTextColor = MaterialTheme.colorScheme.outline
                        ),
                        selected = currentDestination?.hierarchy?.any { it.hasRoute(item.route::class) } == true,
                        onClick = {
                            onMenuItemSelected(item.route)
                        }
                    )
                }
            }
        }
    ) { innerPadding ->
        content(innerPadding)
    }
}
