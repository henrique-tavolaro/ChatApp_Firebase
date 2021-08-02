package com.example.chatapp.domain.repositories

import com.example.chatapp.domain.entity.UserModel
import kotlinx.coroutines.flow.Flow

interface GetUserList {

    suspend fun addUser(user: UserModel)

    fun getUserList(userId: String) : Flow<MutableList<UserModel>?>
}