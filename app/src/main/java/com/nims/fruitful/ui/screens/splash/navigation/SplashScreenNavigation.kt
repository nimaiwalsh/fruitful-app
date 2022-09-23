package com.nims.fruitful.ui.screens.splash.navigation

import androidx.compose.material.ExperimentalMaterialApi
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.nims.fruitful.ui.navigation.FruitfulNavigationDestination
import com.nims.fruitful.ui.screens.splash.SplashScreen

object SplashScreenDestination : FruitfulNavigationDestination {
    override val route = "splash_route"
    override val destination = "splash_destination"
}

@ExperimentalMaterialApi
fun NavGraphBuilder.splashGraph(
    onNavigateToIdeas: () -> Unit
) {
    composable(route = SplashScreenDestination.route) {
        SplashScreen(navigateToIdeas = onNavigateToIdeas)
    }
}