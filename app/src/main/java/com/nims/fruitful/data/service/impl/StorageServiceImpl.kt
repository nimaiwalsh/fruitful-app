package com.nims.fruitful.data.service.impl

import com.google.firebase.firestore.DocumentChange
import com.google.firebase.firestore.ListenerRegistration
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase
import com.nims.fruitful.data.service.StorageService
import com.nims.fruitful.model.Idea
import com.nims.fruitful.model.Task
import javax.inject.Inject

class StorageServiceImpl @Inject constructor() : StorageService {
    private var listenerRegistration: ListenerRegistration? = null

    override fun addListener(
        userId: String,
        onDocumentEvent: (Boolean, Task) -> Unit,
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

    override fun getIdea(
        ideaId: String,
        onError: (Throwable) -> Unit,
        onSuccess: (Task) -> Unit
    ) {
        Firebase.firestore
            .collection(IDEA_COLLECTION)
            .document(ideaId)
            .get()
            .addOnFailureListener { error -> onError(error) }
            .addOnSuccessListener { result -> onSuccess(result.toObject() ?: Task()) }
    }

    override fun saveIdea(idea: Idea, onResult: (Throwable?) -> Unit) {
        Firebase.firestore
            .collection(IDEA_COLLECTION)
            .document(idea.id)
            .set(idea)
            .addOnCompleteListener { onResult(it.exception) }
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