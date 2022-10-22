package com.nims.fruitful.data.service.impl

import com.google.firebase.firestore.DocumentChange
import com.google.firebase.firestore.ListenerRegistration
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase
import com.nims.fruitful.data.service.DataResult
import com.nims.fruitful.data.service.StorageService
import com.nims.fruitful.model.Idea
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class StorageServiceImpl @Inject constructor() : StorageService {
    private var listenerRegistration: ListenerRegistration? = null

    override fun addListener(
        userId: String,
        onDocumentEvent: (Boolean, Idea) -> Unit,
        onError: (Throwable) -> Unit
    ) {

        val query = Firebase.firestore.collection(IDEA_COLLECTION).whereEqualTo(USER_ID, userId)

        listenerRegistration = query.addSnapshotListener { value, error ->
            if (error != null) {
                onError(error)
                return@addSnapshotListener
            }

            value?.documentChanges?.forEach {
                val wasDocumentDeleted = it.type == DocumentChange.Type.REMOVED
                onDocumentEvent(wasDocumentDeleted, it.document.toObject())
            }
        }
    }

    override fun removeListener() {
        listenerRegistration?.remove()
    }

    override suspend fun getIdea(ideaId: String): DataResult<Idea> {
        return try {
            val result = Firebase.firestore
                .collection(IDEA_COLLECTION)
                .document(ideaId)
                .get()
                .await()
            DataResult.Success(result.toObject<Idea>() ?: Idea())
        } catch (e: Exception) {
            DataResult.Failure(e)
        }
    }

    override suspend fun saveIdea(idea: Idea): DataResult<Unit> {
        return try {
            Firebase.firestore
                .collection(IDEA_COLLECTION)
                .document(idea.id)
                .set(idea)
                .await()
            DataResult.Success(Unit)
        } catch (e: Exception) {
            DataResult.Failure(e)
        }
    }

    override fun deleteIdea(ideaId: String, onResult: (Throwable?) -> Unit) {
        Firebase.firestore
            .collection(IDEA_COLLECTION)
            .document(ideaId)
            .delete()
            .addOnCompleteListener { onResult(it.exception) }
    }

    override fun deleteAllForUser(userId: String, onResult: (Throwable?) -> Unit) {
        Firebase.firestore
            .collection(IDEA_COLLECTION)
            .whereEqualTo(USER_ID, userId)
            .get()
            .addOnFailureListener { error -> onResult(error) }
            .addOnSuccessListener { result ->
                for (document in result) document.reference.delete()
                onResult(null)
            }
    }

    override fun updateUserId(
        oldUserId: String,
        newUserId: String,
        onResult: (Throwable?) -> Unit
    ) {
        Firebase.firestore
            .collection(IDEA_COLLECTION)
            .whereEqualTo(USER_ID, oldUserId)
            .get()
            .addOnFailureListener { error -> onResult(error) }
            .addOnSuccessListener { result ->
                for (document in result) document.reference.update(USER_ID, newUserId)
                onResult(null)
            }
    }

    companion object {
        private const val IDEA_COLLECTION = "Idea"
        private const val USER_ID = "userId"
    }
}