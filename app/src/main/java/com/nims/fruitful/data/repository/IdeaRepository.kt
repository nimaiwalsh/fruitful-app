package com.nims.fruitful.data.repository

import com.nims.fruitful.data.service.DataResult
import com.nims.fruitful.data.service.StorageService
import com.nims.fruitful.model.Idea
import javax.inject.Inject

interface IdeaRepository {
    suspend fun getIdea(ideaId: String): DataResult<Idea>
    suspend fun saveIdea(idea: Idea): DataResult<Unit>
}

class IdeaRepositoryImpl @Inject constructor(private val storageService: StorageService) :
    IdeaRepository {
    override suspend fun getIdea(ideaId: String): DataResult<Idea> {
        return storageService.getIdea(ideaId)
    }

    override suspend fun saveIdea(idea: Idea): DataResult<Unit> {
        return storageService.saveIdea(idea)
    }
}