package com.example.chatapp.infra.datasource

import com.example.chatapp.domain.entity.Message
import com.example.chatapp.domain.entity.UserModel
import com.google.firebase.firestore.QuerySnapshot
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class FakeFirestoreDatasource(
    private var userList: MutableList<UserModel>? = mutableListOf(),
    var messageList: MutableList<Message>? = mutableListOf()
) : FirestoreDatasource {

    override suspend fun addUser(user: UserModel) {
        userList?.add(user)
    }

    override fun getUsersList(userId: String): Flow<MutableList<UserModel>?> = flow {
        val users = mutableListOf<UserModel>()
            userList!!.map {
            if(it.id != userId){
                users.add(it)
            }
        }
        emit(users)
    }

    override suspend fun addMessage(message: Message) {
        messageList?.add(message)
    }

    override fun getAllMessages(user1id: String, user2id: String): Flow<QuerySnapshot?> {
        TODO("Not yet implemented")
    }

    override suspend fun getAllUsers(): MutableList<UserModel> {
        return userList!!
    }


}