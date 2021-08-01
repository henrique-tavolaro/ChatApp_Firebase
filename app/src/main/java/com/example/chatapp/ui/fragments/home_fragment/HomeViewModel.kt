package com.example.chatapp.ui.fragments.home_fragment

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.chatapp.domain.entity.UserModel
import com.example.chatapp.infra.repositories.FirestoreRepositoryImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: FirestoreRepositoryImpl
): ViewModel() {


    fun addUser(user: UserModel){
        viewModelScope.launch {
            repository.addUser(user)
        }
    }

    @InternalCoroutinesApi
    @ExperimentalCoroutinesApi
    fun getAllUsers(userId: String) : Flow<MutableList<UserModel>?> {

       return repository.getUserList(userId)
    }

}