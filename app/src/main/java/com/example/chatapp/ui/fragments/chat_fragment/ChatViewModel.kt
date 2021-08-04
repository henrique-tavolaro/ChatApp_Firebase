package com.example.chatapp.ui.fragments.chat_fragment

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.chatapp.domain.entity.Message
import com.example.chatapp.domain.repositories.AddMessage
import com.example.chatapp.domain.repositories.GetMessages
import com.example.chatapp.infra.usecases_repositories.AddMessageUseCase
import com.example.chatapp.infra.usecases_repositories.AddUserUseCase
import com.example.chatapp.infra.usecases_repositories.GetMessagesUseCase
import com.example.chatapp.infra.usecases_repositories.GetUserListUseCase
import com.example.chatapp.ui.fragments.home_fragment.HomeViewModel
import com.google.firebase.firestore.QuerySnapshot
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ChatViewModel @Inject constructor(
    private val addMessageUseCase: AddMessage,
    private val getMessagesUseCase: GetMessages
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

    // function for testing

    suspend fun addMessageTest(message: Message){
        getMessagesUseCase.addMessage(message)
    }

    suspend fun getMessages() : MutableList<Message> {
        return addMessageUseCase.getMessages()
    }
}

@Suppress("UNCHECKED_CAST")
class ChatViewModelFactory (
    private val addMessageUseCase: AddMessageUseCase,
    private val getMessagesUseCase: GetMessagesUseCase
) : ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel> create(modelClass: Class<T>) =
        (ChatViewModel(addMessageUseCase, getMessagesUseCase) as T)
}