package com.nims.fruitful.ui.screens.daily_ideas

import com.nims.fruitful.data.service.AccountService
import com.nims.fruitful.data.service.LogService
import com.nims.fruitful.data.service.StorageService
import com.nims.fruitful.ui.navigation.EDIT_IDEA_SCREEN
import com.nims.fruitful.ui.screens.MainViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class DailyIdeasViewModel @Inject constructor(
    logService: LogService,
    private val storageService: StorageService,
    private val accountService: AccountService
) : MainViewModel(logService) {

    fun onAddClick(openScreen: (String) -> Unit) = openScreen(EDIT_IDEA_SCREEN)

}