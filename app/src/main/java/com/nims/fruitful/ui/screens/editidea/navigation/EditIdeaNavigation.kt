package com.nims.fruitful.ui.screens.editidea.navigation

import android.net.Uri
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.nims.fruitful.ui.navigation.FruitfulNavigationDestination
import com.nims.fruitful.ui.screens.editidea.EditIdeaScreen

object EditIdeaDestination : FruitfulNavigationDestination {
    const val IDEA_DEFAULT_ID = "-1"
    const val IDEA_ID_ARG = "ideaId"
    override val route = "editidea_route/{$IDEA_ID_ARG}"
    override val destination = "editidea_destination"

    /**
     * Creates destination route for a topicId that could include special characters
     */
    fun createNavigationRoute(ideaIdArg: String): String {
        val encodedId = Uri.encode(ideaIdArg.ifBlank { IDEA_DEFAULT_ID })
        return "editidea_route/$encodedId"
    }

    /**
     * Returns the topicId from a [NavBackStackEntry] after a topic destination navigation call
     */
    fun fromNavArgs(entry: NavBackStackEntry): String {
        val encodedId = entry.arguments?.getString(IDEA_ID_ARG)!!
        return Uri.decode(encodedId)
    }
}

fun NavGraphBuilder.editIdeaGraph(
    onBackClick: () -> Unit
) {
    composable(
        route = EditIdeaDestination.route,
        arguments = listOf(
            navArgument(EditIdeaDestination.IDEA_ID_ARG) { type = NavType.StringType }
        )
    ) {
        EditIdeaScreen(
            popUpScreen = { },
            ideaId = it.arguments?.getString(EditIdeaDestination.IDEA_ID_ARG)
                ?: EditIdeaDestination.IDEA_DEFAULT_ID
        )
    }
}