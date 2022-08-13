package com.nims.fruitful

import android.content.res.Resources
import androidx.compose.material.ScaffoldState
import androidx.compose.runtime.Stable
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import com.nims.fruitful.ui.common.icon.FruitfulIcons
import com.nims.fruitful.ui.common.icon.Icon.ImageVectorIcon
import com.nims.fruitful.ui.common.snackbar.SnackbarManager
import com.nims.fruitful.ui.common.snackbar.SnackbarMessage.Companion.toMessage
import com.nims.fruitful.ui.navigation.FruitfulNavigationDestination
import com.nims.fruitful.ui.navigation.IdeasDestination
import com.nims.fruitful.ui.navigation.SavedIdeasDestination
import com.nims.fruitful.ui.navigation.TopLevelDestination
import com.nims.fruitful.ui.screens.saved_ideas.SavedIdeasScreen
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.launch

@Stable
class MainAppState(
    val scaffoldState: ScaffoldState,
    val navController: NavHostController,
    private val snackbarManager: SnackbarManager,
    private val resources: Resources,
    coroutineScope: CoroutineScope
) {
    init {
        coroutineScope.launch {
            snackbarManager.snackbarMessages.filterNotNull().collect { snackbarMessage ->
                val text = snackbarMessage.toMessage(resources)
                scaffoldState.snackbarHostState.showSnackbar(text)
            }
        }
    }

    /**
     * Top level destinations to be used in the BottomBar and NavRail
     */
    val topLevelDestinations: List<TopLevelDestination> = listOf(
        TopLevelDestination(
            route = IdeasDestination.route,
            destination = IdeasDestination.destination,
            selectedIcon = ImageVectorIcon(FruitfulIcons.AccountCircle),
            unselectedIcon = ImageVectorIcon(FruitfulIcons.AccountCircle),
            iconTextId = R.string.ideas
        ),
        TopLevelDestination(
            route = SavedIdeasDestination.route,
            destination = SavedIdeasDestination.destination,
            selectedIcon = ImageVectorIcon(FruitfulIcons.Star),
            unselectedIcon = ImageVectorIcon(FruitfulIcons.Star),
            iconTextId = R.string.saved_ideas
        ),
    )

    fun popUp() {
        navController.popBackStack()
    }

    fun navigate(route: String) {
        navController.navigate(route) {
            launchSingleTop = true
        }
    }

    fun navigateAndPopUp(route: String, popUp: String) {
        navController.navigate(route) {
            launchSingleTop = true
            popUpTo(popUp) { inclusive = true }
        }
    }

    fun clearAndNavigate(route: String) {
        navController.navigate(route) {
            launchSingleTop = true
            popUpTo(0) { inclusive = true }
        }
    }

    /**
     * UI logic for navigating to a particular destination in the app. The NavigationOptions to
     * navigate with are based on the type of destination, which could be a top level destination or
     * just a regular destination.
     *
     * Top level destinations have only one copy of the destination of the back stack, and save and
     * restore state whenever you navigate to and from it.
     * Regular destinations can have multiple copies in the back stack and state isn't saved nor
     * restored.
     *
     * @param destination: The [NiaNavigationDestination] the app needs to navigate to.
     * @param route: Optional route to navigate to in case the destination contains arguments.
     */
    fun navigate(destination: FruitfulNavigationDestination, route: String? = null) {
        if (destination is TopLevelDestination) {
            navController.navigate(route ?: destination.route) {
                // Pop up to the start destination of the graph to
                // avoid building up a large stack of destinations
                // on the back stack as users select items
                popUpTo(navController.graph.findStartDestination().id) {
                    saveState = true
                }
                // Avoid multiple copies of the same destination when
                // reselecting the same item
                launchSingleTop = true
                // Restore state when reselecting a previously selected item
                restoreState = true
            }
        } else {
            navController.navigate(route ?: destination.route)
        }
    }
}