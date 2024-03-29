package com.nims.fruitful.ui.screens.dailyideas

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.viewModelScope
import com.nims.fruitful.data.repository.IdeaRepository
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
    private val ideaRepository: IdeaRepository,
    private val storageService: StorageService,
    private val accountService: AccountService
) : MainViewModel(logService) {

    var ideas = mutableStateListOf<Idea>()
        private set

    // Do not use the ViewModelScope here, we want to use the Compose view scope to cancel this
    // job, which in turn removes the listener.
    suspend fun addListener() {
        storageService.addListener(accountService.getUserId()).collect { result ->
            when (result) {
                is DataResult.Failure -> onError(result.error)
                is DataResult.Success -> updateIdeaInList(result.data)
            }
        }
    }

    fun onIdeaActionClick(navigateToEditIdea: (String) -> Unit, idea: Idea, action: String) {
        when (IdeaActionOption.getByTitle(action)) {
            IdeaActionOption.EditIdea -> navigateToEditIdea(idea.id)
            IdeaActionOption.ToggleFavourite -> onFavouriteIdeaClick(idea)
            IdeaActionOption.DeleteIdea -> onDeleteIdeaClick(idea)
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

    private fun onDeleteIdeaClick(idea: Idea) {
        viewModelScope.launch(showErrorExceptionHandler) {
            when (val result = storageService.deleteIdea(idea.id)) {
                is DataResult.Failure -> onError(result.error)
                is DataResult.Success -> {}
            }
        }
    }

    private fun updateIdeaInList(idea: Idea) {
        if (idea.isRemoved) ideas.remove(idea) else {
            val index = ideas.indexOfFirst { it.id == idea.id }
            if (index < 0) ideas.add(idea) else ideas[index] = idea
        }
    }
}