package com.example.chatapp.ui.fragments.chat_fragment

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ChatViewModel @Inject constructor(

): ViewModel(){

    val message = mutableStateOf("")

    fun onMessageChange(text: String){
        this.message.value = text
    }

}