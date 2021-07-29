package com.example.chatapp.ui.fragments.chat_fragment

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.chatapp.domain.entity.Message
import com.example.chatapp.infra.repositories.FirestoreRepositoryImpl
import com.google.firebase.firestore.QuerySnapshot
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ChatViewModel @Inject constructor(
    private val repository: FirestoreRepositoryImpl
): ViewModel(){

    val message = mutableStateOf("")

    fun onMessageChange(text: String){
        this.message.value = text
    }

    fun addMessage(message: Message){
        viewModelScope.launch {
            repository.addMessage(message)
        }
    }

    @ExperimentalCoroutinesApi
    fun getAllMessages(user1id: String, user2id: String) : Flow<QuerySnapshot?> {
        return repository.getAllMessages(user1id, user2id)

    }
}