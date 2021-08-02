package com.example.chatapp.ui.fragments.home_fragment

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.chatapp.domain.entity.UserModel
import com.example.chatapp.infra.usecases_repositories.AddUserUseCase
import com.example.chatapp.infra.usecases_repositories.GetUserListUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val addUserUseCase: AddUserUseCase,
    private val getUserListUseCase: GetUserListUseCase
): ViewModel() {


    fun addUser(user: UserModel){
        viewModelScope.launch {
            addUserUseCase.addUser(user)
        }
    }

    @InternalCoroutinesApi
    @ExperimentalCoroutinesApi
    fun getAllUsers(userId: String) : Flow<MutableList<UserModel>?> {

       return getUserListUseCase.getUserList(userId)
    }

}