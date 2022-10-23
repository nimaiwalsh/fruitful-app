package com.nims.fruitful.data.service.impl

import android.util.Log
import com.google.firebase.firestore.DocumentChange
import com.google.firebase.firestore.DocumentChange.Type
import com.google.firebase.firestore.ListenerRegistration
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase
import com.nims.fruitful.data.service.DataResult
import com.nims.fruitful.data.service.StorageService
import com.nims.fruitful.model.Idea
import kotlinx.coroutines.cancel
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import kotlin.reflect.KClass

class StorageServiceImpl @Inject constructor() : StorageService {

    private var listenerRegistration: ListenerRegistration? = null

    override suspend fun addListener(userId: String): Flow<DataResult<Idea>> =
        callbackFlow {

            // Create a reference to our data inside Firestore
            val query = Firebase.firestore.collection(IDEA_COLLECTION).whereEqualTo(USER_ID, userId)

            // Generate a subscription that is going to let us listen for changes with
            // .addSnapshotListener and then offer those values to the channel.
            listenerRegistration = query.addSnapshotListener { value, error ->
                Log.i("STORAGE SERVICE", "Snapshot listener added")
                if (error != null) {
                    trySend(DataResult.Failure(error))
                    cancel("Error adding snapshot listener to query", error)
                    return@addSnapshotListener
                }

                value?.documentChanges?.forEach {
                    val change = it.document.toObject<Idea>()
                    change.isRemoved = it.type == DocumentChange.Type.REMOVED
                    trySend(DataResult.Success(change))
                }
            }

            // Finally if collect is not in use or collecting any data we cancel this channel
            // to prevent any leak and remove the subscription listener to the database.
            awaitClose {
                Log.i("STORAGE SERVICE", "Snapshot listener registration removed")
                listenerRegistration?.remove()
            }
        }

    override suspend fun removeListener() {
        Log.i("STORAGE SERVICE", "Snapshot listener registration removed")
        listenerRegistration?.remove()
    }

    override suspend fun getIdea(ideaId: String): DataResult<Idea> {
        return try {
            val result = Firebase.firestore
                .collection(IDEA_COLLECTION)
                .document(ideaId)
                .get()
                .await()
            DataResult.Success(result.toObject() ?: Idea())
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