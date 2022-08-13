package com.nims.fruitful.ui.navigation

import androidx.compose.material.ExperimentalMaterialApi
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.nims.fruitful.ui.screens.savedideas.SavedIdeasScreen

object SavedIdeasDestination : FruitfulNavigationDestination {
    override val route = "saved_ideas_route"
    override val destination = "saved_ideas_destination"
}

@OptIn(ExperimentalMaterialApi::class)
fun NavGraphBuilder.savedIdeasGraph() {
    composable(SAVED_IDEAS_SCREEN) {
        SavedIdeasScreen()
    }
}
