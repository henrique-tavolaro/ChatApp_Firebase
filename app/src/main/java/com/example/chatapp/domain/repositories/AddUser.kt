package com.example.chatapp.domain.repositories

import com.example.chatapp.domain.entity.UserModel

interface AddUser {

    suspend fun addUser(user: UserModel)

    // function for testing
    suspend fun getAllUsers() : MutableList<UserModel>
}