package com.nims.fruitful.ui.navigation

import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.nims.fruitful.ui.screens.dailyideas.navigation.IdeasDestination
import com.nims.fruitful.ui.screens.dailyideas.navigation.ideasGraph
import com.nims.fruitful.ui.screens.editidea.navigation.EditIdeaDestination
import com.nims.fruitful.ui.screens.editidea.navigation.editIdeaGraph
import com.nims.fruitful.ui.screens.savedideas.navigation.savedIdeasGraph
import com.nims.fruitful.ui.screens.splash.navigation.SplashScreenDestination
import com.nims.fruitful.ui.screens.splash.navigation.splashGraph

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun FruitfulNavHost(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    onNavigateToDestination: (FruitfulNavigationDestination, String?) -> Unit,
    onBackClick: () -> Unit,
    startDestination: String = SplashScreenDestination.route,
) {
    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier,
    ) {
        splashGraph(onNavigateToIdeas = { onNavigateToDestination(IdeasDestination, null) })
        ideasGraph(
            navigateToEditIdea = {
                onNavigateToDestination(
                    EditIdeaDestination,
                    EditIdeaDestination.createNavigationRoute(it)
                )
            },
            nestedGraphs = {
                editIdeaGraph(onBackClick)
            }
        )
        savedIdeasGraph()
    }
}