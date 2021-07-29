package com.example.chatapp.infra.datasource

import android.util.Log
import androidx.lifecycle.Transformations.map
import com.example.chatapp.domain.entity.Message
import com.example.chatapp.domain.entity.UserModel
import com.example.chatapp.utils.MESSAGES
import com.example.chatapp.utils.USERS
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.firestore.SetOptions
import com.google.firebase.firestore.ktx.toObject
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FirestoreDatasourceImpl @Inject constructor(
    private val firestore: FirebaseFirestore
) : FirestoreDatasource {

    override suspend fun addUser(user: UserModel) {
        firestore.collection(USERS)
            .document(user.id)
            .set(user, SetOptions.merge())
            .addOnSuccessListener {
                Log.d("TAG", "success")
            }

    }

    @ExperimentalCoroutinesApi
    override fun getAllUsers(userId: String) = callbackFlow {
        val collection = firestore.collection(USERS).whereNotEqualTo("id", userId)

        val snapshotListener = collection.addSnapshotListener() { snapshot, e ->
            this.trySend(snapshot).isSuccess
        }

        awaitClose {
            snapshotListener.remove()
        }
    }

    override suspend fun addMessage(message: Message) {
        firestore.collection(MESSAGES)
            .document(message.id)
            .set(message)
    }

    @ExperimentalCoroutinesApi
    override fun getAllMessages(user1id: String, user2id: String) = callbackFlow {
        val collection = firestore.collection(MESSAGES).whereIn("conversation", listOf(user1id + user2id, user2id + user1id))

        val snapshotListener = collection.addSnapshotListener() { snapshot, e ->
            this.trySend(snapshot).isSuccess
        }

        awaitClose {
            snapshotListener.remove()
        }
    }


}