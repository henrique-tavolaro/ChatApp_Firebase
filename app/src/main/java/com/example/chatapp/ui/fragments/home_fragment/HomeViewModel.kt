package com.example.chatapp.ui.fragments.home_fragment

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.chatapp.domain.entity.UserModel
import com.example.chatapp.domain.repositories.AddUser
import com.example.chatapp.domain.repositories.GetUserList
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
    private val addUserUseCase: AddUser,
    private val getUserListUseCase: GetUserList
): ViewModel() {


    fun addUser(user: UserModel){
        viewModelScope.launch {
            addUserUseCase.addUser(user)
        }
    }

    @InternalCoroutinesApi
    @ExperimentalCoroutinesApi
    fun getUserList(userId: String) : Flow<MutableList<UserModel>?> {
       return getUserListUseCase.getUserList(userId)
    }


    // functions for testing

    suspend fun getAllUsers() : MutableList<UserModel> {
        return addUserUseCase.getAllUsers()
    }

    suspend fun addUserTest(user: UserModel){
            getUserListUseCase.addUser(user)
    }

}

@Suppress("UNCHECKED_CAST")
class HomeViewModelFactory (
    private val addUserUseCase: AddUserUseCase,
    private val getUserListUseCase: GetUserListUseCase
) : ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel> create(modelClass: Class<T>) =
        (HomeViewModel(addUserUseCase, getUserListUseCase) as T)
}