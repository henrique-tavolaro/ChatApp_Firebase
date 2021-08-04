package com.example.chatapp.infra.fake_usecases_repositories

import com.example.chatapp.domain.entity.Message
import com.example.chatapp.domain.repositories.GetMessages
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class FakeGetMessagesUseCase(
    private val messageList: MutableList<Message>? = mutableListOf()
) : GetMessages{
    override fun getAllMessages(user1id: String, user2id: String): Flow<MutableList<Message>?> = flow {
        val list = mutableListOf<Message>()
        messageList!!.map{
            if(it.conversation == user1id + user2id || it.conversation == user2id + user1id){
                list.add(it)
            }
        }
        emit(list)
    }

    override suspend fun addMessage(message: Message) {
        messageList!!.add(message)
    }
}