package com.nims.fruitful.ui.navigation

import androidx.compose.material.ExperimentalMaterialApi
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.nims.fruitful.MainAppState
import com.nims.fruitful.ui.screens.dailyideas.DailyIdeasScreen
import com.nims.fruitful.ui.screens.login.LoginScreen
import com.nims.fruitful.ui.screens.splash.SplashScreen

@ExperimentalMaterialApi
fun NavGraphBuilder.fruitfulGraph(appState: MainAppState) {
    composable(SPLASH_SCREEN) {
        SplashScreen(openAndPopUp = { route, popUp -> appState.navigateAndPopUp(route, popUp) })
    }

    composable(DAILY_IDEAS_SCREEN) {
        DailyIdeasScreen(openScreen = { route -> appState.navigate(route) })
    }

    composable(LOGIN_SCREEN) {
        LoginScreen(openAndPopUp = { route, popUp -> appState.navigateAndPopUp(route, popUp) })
    }
//
//    composable(SETTINGS_SCREEN) {
//        SettingsScreen(
//            restartApp = { route -> appState.clearAndNavigate(route) },
//            openScreen = { route -> appState.navigate(route) }
//        )
//    }

//    composable(SIGN_UP_SCREEN) {
//        SignUpScreen(openAndPopUp = { route, popUp -> appState.navigateAndPopUp(route, popUp) })
//    }
//
//    composable(TASKS_SCREEN) {
//        TasksScreen(openScreen = { route -> appState.navigate(route) })
//    }
//
//    composable(
//        route = "$EDIT_TASK_SCREEN$TASK_ID_ARG",
//        arguments = listOf(navArgument(TASK_ID) { defaultValue = TASK_DEFAULT_ID })
//    ) {
//        EditTaskScreen(
//            popUpScreen = { appState.popUp() },
//            taskId = it.arguments?.getString(TASK_ID) ?: TASK_DEFAULT_ID
//        )
//    }

}
