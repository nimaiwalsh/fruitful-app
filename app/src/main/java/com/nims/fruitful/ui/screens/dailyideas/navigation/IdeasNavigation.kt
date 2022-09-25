package com.nims.fruitful.ui.screens.dailyideas.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.nims.fruitful.ui.navigation.FruitfulNavigationDestination
import com.nims.fruitful.ui.screens.dailyideas.DailyIdeasScreen

object IdeasDestination : FruitfulNavigationDestination {
    override val route = "ideas_route"
    override val destination = "ideas_destination"
}

fun NavGraphBuilder.ideasGraph(
    navigateToEditIdea: (String) -> Unit,
    nestedGraphs: NavGraphBuilder.() -> Unit,
) {
    navigation(
        route = IdeasDestination.route,
        startDestination = IdeasDestination.destination
    ) {
        composable(route = IdeasDestination.destination) {
            DailyIdeasScreen(navigateToEditIdea = navigateToEditIdea)
        }
        nestedGraphs()
    }
}
