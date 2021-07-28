package com.example.chatapp.domain.repositories

import com.example.chatapp.domain.entity.UserModel
import com.google.firebase.firestore.QuerySnapshot
import kotlinx.coroutines.flow.Flow

interface FirestoreRepository {

    suspend fun addUser(user: UserModel)

    fun getAllUsers(): Flow<QuerySnapshot?>
//    : List<UserModel>
}