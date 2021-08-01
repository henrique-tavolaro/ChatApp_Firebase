package com.example.chatapp.infra.repositories

import com.example.chatapp.domain.entity.Message
import com.example.chatapp.domain.entity.UserModel
import com.example.chatapp.domain.repositories.FirestoreRepository
import com.example.chatapp.infra.datasource.FirestoreDatasource
import com.google.firebase.firestore.QuerySnapshot
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton



@Singleton
class FirestoreRepositoryImpl @Inject constructor(
    private val firestoreClass: FirestoreDatasource
) : FirestoreRepository{

    override suspend fun addUser(user: UserModel){
        firestoreClass.addUser(user)
    }

    @ExperimentalCoroutinesApi
    override fun getUserList(userId: String) : Flow<MutableList<UserModel>?> {
         return firestoreClass.getUsersList(userId)
    }

    override suspend fun addMessage(message: Message) {
        firestoreClass.addMessage(message)
    }

    @ExperimentalCoroutinesApi
    override fun getAllMessages(user1id: String, user2id: String): Flow<QuerySnapshot?> {
        return firestoreClass.getAllMessages(user1id, user2id)
    }

    override suspend fun getAllUsers(): MutableList<UserModel> {
        return firestoreClass.getAllUsers()
    }


}