package com.nims.fruitful.ui.screens.editidea

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.viewModelScope
import com.nims.fruitful.data.repository.IdeaRepository
import com.nims.fruitful.data.service.AccountService
import com.nims.fruitful.data.service.DataResult
import com.nims.fruitful.data.service.LogService
import com.nims.fruitful.model.Idea
import com.nims.fruitful.ui.screens.MainViewModel
import com.nims.fruitful.ui.screens.editidea.navigation.EditIdeaDestination
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EditIdeaViewModel @Inject constructor(
    logService: LogService,
    private val ideaRepository: IdeaRepository,
    private val accountService: AccountService
) : MainViewModel(logService) {

    var uiState = mutableStateOf(EditIdeaUiState(Idea()))
        private set

    fun initialize(ideaId: String) {
        viewModelScope.launch {
            // Only fetch an idea if it has a real id,
            // if it is using a default id we are creating a new idea.
            if (ideaId != EditIdeaDestination.IDEA_DEFAULT_ID) {
                when (val result = ideaRepository.getIdea(ideaId)) {
                    is DataResult.Success -> uiState.value = EditIdeaUiState(idea = result.data)
                    is DataResult.Failure -> onError(result.error)
                }
            }
        }
    }

    fun onTitleChange(newValue: String) {
        uiState.value = uiState.value.copy(idea = uiState.value.idea.copy(title = newValue))
    }

    fun onDescriptionChange(newValue: String) {
        uiState.value = uiState.value.copy(idea = uiState.value.idea.copy(description = newValue))
    }

    fun onDoneClick(navigateBack: () -> Unit) {
        viewModelScope.launch(showErrorExceptionHandler) {
            val editedIdea = uiState.value.idea.copy(userId = accountService.getUserId())

            when (val result = ideaRepository.saveIdea(editedIdea)) {
                is DataResult.Failure -> onError(result.error)
                is DataResult.Success -> navigateBack()
            }
        }
    }

    data class EditIdeaUiState(
        val idea: Idea
    )
}