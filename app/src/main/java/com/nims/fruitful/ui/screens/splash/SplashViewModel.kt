package com.nims.fruitful.ui.screens.splash

import androidx.lifecycle.viewModelScope
import com.nims.fruitful.data.service.AccountService
import com.nims.fruitful.data.service.LogService
import com.nims.fruitful.ui.screens.MainViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val accountService: AccountService,
    private val logService: LogService
) : MainViewModel(logService) {

    fun onAppStart(navigateToIdeas: () -> Unit) {
        if (accountService.hasUser()) navigateToIdeas()
        else createAnonymousAccount(navigateToIdeas)
    }

    private fun createAnonymousAccount(navigateToIdeas: () -> Unit) {
        viewModelScope.launch(logErrorExceptionHandler) {
            accountService.createAnonymousAccount { error ->
                if (error != null) logService.logNonFatalCrash(error)
                else navigateToIdeas()
            }
        }
    }
}