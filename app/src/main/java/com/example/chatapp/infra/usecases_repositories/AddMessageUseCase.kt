package com.example.chatapp.infra.usecases_repositories

import com.example.chatapp.domain.entity.Message
import com.example.chatapp.domain.repositories.AddMessage
import com.example.chatapp.infra.datasource.FirestoreDatasource
import com.google.firebase.firestore.QuerySnapshot
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AddMessageUseCase @Inject constructor(
    private val firestore: FirestoreDatasource
) : AddMessage {

    override suspend fun addMessage(message: Message) {
        firestore.addMessage(message)
    }

    // function for testing
    @ExperimentalCoroutinesApi
    override suspend fun getMessages(user1id: String, user2id: String): MutableList<Message> {
       return firestore.getMessages(user1id, user2id)
    }

}