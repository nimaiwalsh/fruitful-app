package com.nims.fruitful.ui.screens.editidea

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.viewModelScope
import com.nims.fruitful.data.service.AccountService
import com.nims.fruitful.data.service.LogService
import com.nims.fruitful.data.service.StorageService
import com.nims.fruitful.model.Idea
import com.nims.fruitful.ui.screens.MainViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EditIdeaViewModel @Inject constructor(
    logService: LogService,
    private val storageService: StorageService,
    private val accountService: AccountService
) : MainViewModel(logService) {

    var idea = mutableStateOf(Idea())
        private set

    fun onTitleChange(newValue: String) {
        idea.value = idea.value.copy(title = newValue)
    }

    fun onDescriptionChange(newValue: String) {
        idea.value = idea.value.copy(description = newValue)
    }

    fun onDoneClick(navigateBack: () -> Unit) {
        viewModelScope.launch(showErrorExceptionHandler) {
            val editedIdea = idea.value.copy(userId = accountService.getUserId())

            storageService.saveIdea(editedIdea) { error ->
                if (error == null) navigateBack() else onError(error)
            }
        }
    }
}