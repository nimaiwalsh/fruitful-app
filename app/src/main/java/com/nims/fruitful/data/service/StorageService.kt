package com.nims.fruitful.data.service

import com.nims.fruitful.model.Idea

interface StorageService {
    fun addListener(
        userId: String,
        onDocumentEvent: (Boolean, Idea) -> Unit,
        onError: (Throwable) -> Unit
    )

    fun removeListener()
    fun getIdea(ideaId: String, onError: (Throwable) -> Unit, onSuccess: (Idea) -> Unit)
    fun saveIdea(idea: Idea, onResult: (Throwable?) -> Unit)
    fun deleteIdea(ideaId: String, onResult: (Throwable?) -> Unit)
    fun deleteAllForUser(userId: String, onResult: (Throwable?) -> Unit)
    fun updateUserId(oldUserId: String, newUserId: String, onResult: (Throwable?) -> Unit)
}