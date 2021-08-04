package com.example.chatapp.domain.repositories

import com.example.chatapp.domain.entity.UserModel
import kotlinx.coroutines.flow.Flow

interface GetUserList {

    fun getUserList(userId: String) : Flow<MutableList<UserModel>?>

    //function for testing
    suspend fun addUser(user: UserModel)
}