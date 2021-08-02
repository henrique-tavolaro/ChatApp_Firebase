package com.example.chatapp.infra.usecases_repositories

import com.example.chatapp.domain.entity.Message
import com.example.chatapp.domain.repositories.GetMessages
import com.example.chatapp.infra.datasource.FirestoreDatasource
import com.google.firebase.firestore.QuerySnapshot
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GetMessagesUseCase @Inject constructor(
    private val firestore: FirestoreDatasource
) : GetMessages {

    @ExperimentalCoroutinesApi
    override fun getAllMessages(user1id: String, user2id: String): Flow<MutableList<Message>?> {
        return firestore.getAllMessages(user1id, user2id)
    }

    //function for testing
    override suspend fun addMessage(message: Message) {
        firestore.addMessage(message)
    }
}
