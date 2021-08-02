package com.example.chatapp.infra.usecases_repositories

import com.example.chatapp.domain.entity.UserModel
import com.example.chatapp.domain.repositories.AddUser
import com.example.chatapp.infra.datasource.FirestoreDatasource
import javax.inject.Inject

class AddUserUseCase @Inject constructor(
    private val firestore: FirestoreDatasource
) : AddUser {

    override suspend fun addUser(user: UserModel){
        firestore.addUser(user)
    }

    // function for testing
    override suspend fun getAllUsers(): MutableList<UserModel> {
        return firestore.getAllUsers()
    }


}