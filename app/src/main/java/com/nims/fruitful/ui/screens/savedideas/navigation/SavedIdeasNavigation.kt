package com.nims.fruitful.ui.screens.savedideas.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.nims.fruitful.ui.navigation.FruitfulNavigationDestination
import com.nims.fruitful.ui.screens.savedideas.SavedIdeasScreen

object SavedIdeasDestination : FruitfulNavigationDestination {
    override val route = "saved_ideas_route"
    override val destination = "saved_ideas_destination"
}

fun NavGraphBuilder.savedIdeasGraph() {
    composable(route = SavedIdeasDestination.route) {
        SavedIdeasScreen()
    }
}
