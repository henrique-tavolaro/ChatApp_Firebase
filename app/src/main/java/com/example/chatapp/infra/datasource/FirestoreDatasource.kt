package com.example.chatapp.infra.datasource

import com.example.chatapp.domain.entity.Message
import com.example.chatapp.domain.entity.UserModel
import com.google.firebase.firestore.QuerySnapshot
import kotlinx.coroutines.flow.Flow

interface FirestoreDatasource {

    suspend fun addUser(user: UserModel)

    fun getAllUsers(userId: String) : Flow<QuerySnapshot?>

    suspend fun addMessage(message: Message)

    fun getAllMessages(user1id: String, user2id: String) : Flow<QuerySnapshot?>
}