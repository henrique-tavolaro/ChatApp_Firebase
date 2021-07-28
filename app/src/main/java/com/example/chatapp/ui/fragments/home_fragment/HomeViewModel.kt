package com.example.chatapp.ui.fragments.home_fragment

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.chatapp.domain.entity.UserModel
import com.example.chatapp.infra.repositories.FirestoreRepositoryImpl
import com.google.firebase.firestore.QuerySnapshot
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: FirestoreRepositoryImpl
): ViewModel() {

    val userList = mutableStateOf(listOf<UserModel>())

    fun addUser(user: UserModel){
        viewModelScope.launch {
            repository.addUser(user)
        }
    }

//    init {
//        getAllUsers()
//    }

    @ExperimentalCoroutinesApi
    fun getAllUsers() : Flow<QuerySnapshot?> {
       return repository.getAllUsers()

    }


}