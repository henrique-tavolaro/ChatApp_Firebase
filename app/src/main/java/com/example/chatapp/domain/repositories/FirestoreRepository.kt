package com.example.chatapp.domain.repositories

import com.example.chatapp.domain.entity.Message
import com.example.chatapp.domain.entity.UserModel
import com.google.firebase.firestore.QuerySnapshot
import kotlinx.coroutines.flow.Flow

interface FirestoreRepository {

    suspend fun addUser(user: UserModel)

    fun getUserList(userId: String): Flow<MutableList<UserModel>?>

    suspend fun addMessage(message: Message)

    fun getAllMessages(user1id: String, user2id: String): Flow<QuerySnapshot?>

    suspend fun getAllUsers() : MutableList<UserModel>
}