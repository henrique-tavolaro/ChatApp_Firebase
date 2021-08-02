package com.example.chatapp.infra.usecases_repositories

import com.example.chatapp.domain.entity.UserModel
import com.example.chatapp.domain.repositories.GetUserList
import com.example.chatapp.infra.datasource.FirestoreDatasource
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GetUserListUseCase @Inject constructor(
    private val firestore: FirestoreDatasource
): GetUserList{

    // function for testing
    override suspend fun addUser(user: UserModel){
        firestore.addUser(user)
    }


    @ExperimentalCoroutinesApi
    override fun getUserList(userId: String) : Flow<MutableList<UserModel>?> {
        return firestore.getUsersList(userId)
    }


}