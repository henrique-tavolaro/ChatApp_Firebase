package com.example.chatapp.ui.fragments.chat_fragment

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.chatapp.domain.entity.Message
import com.example.chatapp.infra.usecases_repositories.AddMessageUseCase
import com.example.chatapp.infra.usecases_repositories.GetMessagesUseCase
import com.google.firebase.firestore.QuerySnapshot
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ChatViewModel @Inject constructor(
    private val addMessageUseCase: AddMessageUseCase,
    private val getMessagesUseCase: GetMessagesUseCase
): ViewModel(){

    val message = mutableStateOf("")

    fun onMessageChange(text: String){
        this.message.value = text
    }

    fun addMessage(message: Message){
        viewModelScope.launch {
            addMessageUseCase.addMessage(message)
        }
    }

    @ExperimentalCoroutinesApi
    fun getAllMessages(user1id: String, user2id: String) : Flow<MutableList<Message>?> {
        return getMessagesUseCase.getAllMessages(user1id, user2id)

    }
}