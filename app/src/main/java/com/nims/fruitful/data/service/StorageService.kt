package com.nims.fruitful.data.service

import com.nims.fruitful.model.Idea
import kotlinx.coroutines.flow.Flow

interface StorageService {
    suspend fun addListener(userId: String): Flow<DataResult<Idea>>
    suspend fun removeListener()
    suspend fun getIdea(ideaId: String): DataResult<Idea>
    suspend fun saveIdea(idea: Idea): DataResult<Unit>
    fun deleteIdea(ideaId: String, onResult: (Throwable?) -> Unit)
    fun deleteAllForUser(userId: String, onResult: (Throwable?) -> Unit)
    fun updateUserId(oldUserId: String, newUserId: String, onResult: (Throwable?) -> Unit)
}

sealed class DataResult<out T> {
//    class Loading<out T> : DataResult<T>()

    data class Success<out T>(
        val data: T
    ) : DataResult<T>()

    data class Failure<out T>(
        val error: Throwable
    ) : DataResult<T>()
}