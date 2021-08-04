package com.example.chatapp.domain.repositories

import com.example.chatapp.domain.entity.Message
import com.google.firebase.firestore.QuerySnapshot
import kotlinx.coroutines.flow.Flow

interface AddMessage {

    suspend fun addMessage(message: Message)

    // function for testing
    suspend fun getMessages(): MutableList<Message>
}