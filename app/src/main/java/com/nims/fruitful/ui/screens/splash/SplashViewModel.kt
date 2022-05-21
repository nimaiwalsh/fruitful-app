package com.nims.fruitful.ui.screens.splash

import androidx.lifecycle.viewModelScope
import com.nims.fruitful.data.service.AccountService
import com.nims.fruitful.data.service.LogService
import com.nims.fruitful.ui.navigation.DAILY_IDEAS_SCREEN
import com.nims.fruitful.ui.navigation.SPLASH_SCREEN
import com.nims.fruitful.ui.screens.MainViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val accountService: AccountService,
    private val logService: LogService
) : MainViewModel(logService) {

    fun onAppStart(openAndPopUp: (String, String) -> Unit) {
        if (accountService.hasUser()) openAndPopUp(DAILY_IDEAS_SCREEN, SPLASH_SCREEN)
        else createAnonymousAccount(openAndPopUp)
    }

    private fun createAnonymousAccount(openAndPopUp: (String, String) -> Unit) {
        viewModelScope.launch(logErrorExceptionHandler) {
            accountService.createAnonymousAccount { error ->
                if (error != null) logService.logNonFatalCrash(error)
                else openAndPopUp(DAILY_IDEAS_SCREEN, SPLASH_SCREEN)
            }
        }
    }
}