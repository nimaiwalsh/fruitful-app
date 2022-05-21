package com.nims.fruitful.ui.screens.login

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.viewModelScope
import com.nims.fruitful.R.string as AppText
import com.nims.fruitful.data.service.AccountService
import com.nims.fruitful.data.service.LogService
import com.nims.fruitful.data.service.StorageService
import com.nims.fruitful.ui.common.ext.isValidEmail
import com.nims.fruitful.ui.common.snackbar.SnackbarManager
import com.nims.fruitful.ui.navigation.LOGIN_SCREEN
import com.nims.fruitful.ui.navigation.SETTINGS_SCREEN
import com.nims.fruitful.ui.screens.MainViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class LoginViewModel @Inject constructor(
    private val accountService: AccountService,
    private val storageService: StorageService,
    private val logService: LogService
) : MainViewModel(logService) {
    var uiState = mutableStateOf(LoginUiState())
        private set

    private val email get() = uiState.value.email
    private val password get() = uiState.value.password

    fun onEmailChange(newValue: String) {
        uiState.value = uiState.value.copy(email = newValue)
    }

    fun onPasswordChange(newValue: String) {
        uiState.value = uiState.value.copy(password = newValue)
    }

    fun onSignInClick(openAndPopUp: (String, String) -> Unit) {
        if (!email.isValidEmail()) {
            SnackbarManager.showMessage(AppText.email_error)
            return
        }

        if (password.isBlank()) {
            SnackbarManager.showMessage(AppText.empty_password_error)
            return
        }

        viewModelScope.launch(showErrorExceptionHandler) {
            accountService.authenticate(email, password) { error ->
                if (error == null) {
                    linkWithEmail()
                    updateUserId(openAndPopUp)
                } else onError(error)
            }
        }
    }

    private fun linkWithEmail() {
        viewModelScope.launch(showErrorExceptionHandler) {
            accountService.linkAccount(email, password) { error ->
                if (error != null) logService.logNonFatalCrash(error)
            }
        }
    }

    private fun updateUserId(openAndPopUp: (String, String) -> Unit) {
        viewModelScope.launch(showErrorExceptionHandler) {
            val oldUserId = accountService.getAnonymousUserId()
            val newUserId = accountService.getUserId()

            storageService.updateUserId(oldUserId, newUserId) { error ->
                if (error != null) logService.logNonFatalCrash(error)
                else openAndPopUp(SETTINGS_SCREEN, LOGIN_SCREEN)
            }
        }
    }

    fun onForgotPasswordClick() {
        if (!email.isValidEmail()) {
            SnackbarManager.showMessage(AppText.email_error)
            return
        }

        viewModelScope.launch(showErrorExceptionHandler) {
            accountService.sendRecoveryEmail(email) { error ->
                if (error != null) onError(error)
                else SnackbarManager.showMessage(AppText.recovery_email_sent)
            }
        }
    }
}