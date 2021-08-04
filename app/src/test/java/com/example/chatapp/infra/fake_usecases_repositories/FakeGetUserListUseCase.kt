package com.example.chatapp.infra.fake_usecases_repositories

import com.example.chatapp.domain.entity.UserModel
import com.example.chatapp.domain.repositories.GetUserList
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class FakeGetUserListUseCase(
    private val userList: MutableList<UserModel>? = mutableListOf()
) : GetUserList {
    override suspend fun addUser(user: UserModel) {
        userList!!.add(user)
    }

    override fun getUserList(userId: String): Flow<MutableList<UserModel>?> = flow {
        val list = mutableListOf<UserModel>()
        userList!!.map {
            if(it.id != userId){
                list.add(it)
            }
        }
        emit(list)
    }
}