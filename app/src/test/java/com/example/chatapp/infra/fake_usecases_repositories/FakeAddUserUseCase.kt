package com.example.chatapp.infra.fake_usecases_repositories

import com.example.chatapp.domain.entity.UserModel
import com.example.chatapp.domain.repositories.AddUser
class FakeAddUserUseCase(
    private val userList: MutableList<UserModel> = mutableListOf()
) : AddUser {


    override suspend fun addUser(user: UserModel) {
        userList.add(user)
    }

    override suspend fun getAllUsers(): MutableList<UserModel> {
        return userList
    }
}