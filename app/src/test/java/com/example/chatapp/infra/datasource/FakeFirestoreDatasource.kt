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
        val list = mutableListOf<UserModel>()
        userList!!.map {
            if (it.id != userId) {
                list.add(it)
            }
        }
        emit(list)
    }

    override suspend fun addMessage(message: Message) {
        messageList?.add(message)
    }

    override fun getAllMessages(user1id: String, user2id: String): Flow<MutableList<Message>?> =
        flow {
            val list = mutableListOf<Message>()
            messageList!!.map {
                if (it.conversation == user1id + user2id || it.conversation == user2id + user1id) {
                    list.add(it)
                }
            }
            emit(list)
        }

    override suspend fun getAllUsers(): MutableList<UserModel> {
        return userList!!
    }

    override suspend fun getMessages(): MutableList<Message> {
        return messageList!!
    }


}