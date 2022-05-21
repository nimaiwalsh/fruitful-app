package com.nims.fruitful.ui.screens

import androidx.lifecycle.ViewModel
import com.nims.fruitful.data.service.LogService
import com.nims.fruitful.ui.common.snackbar.SnackbarManager
import com.nims.fruitful.ui.common.snackbar.SnackbarMessage.Companion.toSnackbarMessage
import kotlinx.coroutines.CoroutineExceptionHandler

open class MainViewModel(private val logService: LogService) : ViewModel() {
    open val showErrorExceptionHandler = CoroutineExceptionHandler { _, throwable ->
        onError(throwable)
    }

    open val logErrorExceptionHandler = CoroutineExceptionHandler { _, throwable ->
        logService.logNonFatalCrash(throwable)
    }

    open fun onError(error: Throwable) {
        SnackbarManager.showMessage(error.toSnackbarMessage())
        logService.logNonFatalCrash(error)
    }
}