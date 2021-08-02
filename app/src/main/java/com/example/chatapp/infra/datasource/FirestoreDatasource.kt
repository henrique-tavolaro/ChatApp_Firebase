package com.example.chatapp.infra.datasource

import com.example.chatapp.domain.entity.Message
import com.example.chatapp.domain.entity.UserModel
import com.google.firebase.firestore.QuerySnapshot
import kotlinx.coroutines.flow.Flow
import javax.inject.Singleton

@Singleton
interface FirestoreDatasource {

    suspend fun addUser(user: UserModel)

    fun getUsersList(userId: String) : Flow<MutableList<UserModel>?>

    suspend fun addMessage(message: Message)

    fun getAllMessages(user1id: String, user2id: String) : Flow<MutableList<Message>?>

    // for testing
    suspend fun getAllUsers() : MutableList<UserModel>

    suspend fun getMessages(user1id: String, user2id: String) : MutableList<Message>
}