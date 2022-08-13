package com.nims.fruitful.ui.navigation

import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost

@Composable
fun FruitfulNavHost(
    navController: NavHostController,
    onNavigateToDestination: (FruitfulNavigationDestination, String) -> Unit,
    onBackClick: () -> Unit,
    windowSizeClass: WindowSizeClass,
    modifier: Modifier = Modifier,
    startDestination: String = IdeasDestination.route
) {
    NavHost(
        navController = navController,
        startDestination = SPLASH_SCREEN,
        modifier = modifier,
    ) {
//        ideasGraph(appState)
          savedIdeasGraph()
    }
}