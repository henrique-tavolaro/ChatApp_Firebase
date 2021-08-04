package com.example.chatapp.infra.fake_usecases_repositories

import com.example.chatapp.domain.entity.Message
import com.example.chatapp.domain.repositories.AddMessage

class FakeAddMessagesUseCase(
    private val messageList: MutableList<Message>? = mutableListOf()
) : AddMessage {

    override suspend fun addMessage(message: Message) {
        messageList!!.add(message)
    }

    override suspend fun getMessages(): MutableList<Message> {
        return messageList!!
    }
}