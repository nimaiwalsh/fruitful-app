package com.nims.fruitful.ui.screens.dailyideas

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.viewModelScope
import com.nims.fruitful.data.service.AccountService
import com.nims.fruitful.data.service.DataResult
import com.nims.fruitful.data.service.LogService
import com.nims.fruitful.data.service.StorageService
import com.nims.fruitful.model.Idea
import com.nims.fruitful.ui.screens.MainViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DailyIdeasViewModel @Inject constructor(
    logService: LogService,
    private val storageService: StorageService,
    private val accountService: AccountService
) : MainViewModel(logService) {

    var ideas = mutableStateListOf<Idea>()
        private set


    fun addListener() {
        viewModelScope.launch(showErrorExceptionHandler) {
            storageService.addListener(accountService.getUserId(), ::onDocumentEvent, ::onError)
        }
    }

    fun removeListener() {
        viewModelScope.launch(showErrorExceptionHandler) { storageService.removeListener() }
    }

    fun onIdeaActionClick(navigateToEditIdea: (String) -> Unit, idea: Idea, action: String) {
        when (IdeaActionOption.getByTitle(action)) {
            IdeaActionOption.EditIdea -> navigateToEditIdea(idea.id)
            IdeaActionOption.ToggleFavourite -> onFavouriteIdeaClick(idea)
            IdeaActionOption.DeleteIdea -> onDeleteTaskClick(idea)
        }
    }

    // private helpers
    private fun onFavouriteIdeaClick(idea: Idea) {
        viewModelScope.launch(showErrorExceptionHandler) {
            val updatedIdea = idea.copy(favourite = !idea.favourite)

            when (val result = storageService.saveIdea(updatedIdea)) {
                is DataResult.Failure -> onError(result.error)
                is DataResult.Success -> {}
            }
        }
    }

    private fun onDeleteTaskClick(idea: Idea) {
        viewModelScope.launch(showErrorExceptionHandler) {
            storageService.deleteIdea(idea.id) { error ->
                if (error != null) onError(error)
            }
        }
    }


    private fun onDocumentEvent(wasDocumentDeleted: Boolean, idea: Idea) {
        if (wasDocumentDeleted) ideas.remove(idea) else updateIdeaInList(idea)
    }

    private fun updateIdeaInList(idea: Idea) {
        val index = ideas.indexOfFirst { it.id == idea.id }
        if (index < 0) ideas.add(idea) else ideas[index] = idea
    }

}