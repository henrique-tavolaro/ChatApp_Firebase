package com.example.chatapp.infra.datasource

import com.example.chatapp.domain.entity.UserModel
import com.google.firebase.firestore.QuerySnapshot
import kotlinx.coroutines.flow.Flow

interface FirestoreDatasource {

    suspend fun addUser(user: UserModel)

    fun getAllUsers() : Flow<QuerySnapshot?>
}