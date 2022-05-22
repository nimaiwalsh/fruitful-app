package com.nims.fruitful.data.service

import com.nims.fruitful.model.Idea
import com.nims.fruitful.model.Task

interface StorageService {
    fun addListener(
        userId: String,
        onDocumentEvent: (Boolean, Task) -> Unit,
        onError: (Throwable) -> Unit
    )

    fun removeListener()
    fun getIdea(ideaId: String, onError: (Throwable) -> Unit, onSuccess: (Task) -> Unit)
    fun saveIdea(idea: Idea, onResult: (Throwable?) -> Unit)
    fun deleteIdea(ideaId: String, onResult: (Throwable?) -> Unit)
    fun deleteAllForUser(userId: String, onResult: (Throwable?) -> Unit)
    fun updateUserId(oldUserId: String, newUserId: String, onResult: (Throwable?) -> Unit)
}