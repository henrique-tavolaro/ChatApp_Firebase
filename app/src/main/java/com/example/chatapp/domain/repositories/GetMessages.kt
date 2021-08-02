package com.example.chatapp.domain.repositories

import com.example.chatapp.domain.entity.Message
import com.google.firebase.firestore.QuerySnapshot
import kotlinx.coroutines.flow.Flow

interface GetMessages {

    fun getAllMessages(user1id: String, user2id: String): Flow<MutableList<Message>?>

    //function for testing
    suspend fun addMessage(message: Message)

}