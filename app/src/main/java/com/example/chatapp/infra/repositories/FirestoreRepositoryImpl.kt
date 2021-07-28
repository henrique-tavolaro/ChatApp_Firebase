package com.example.chatapp.infra.repositories

import android.util.Log
import com.example.chatapp.domain.entity.UserModel
import com.example.chatapp.domain.repositories.FirestoreRepository
import com.example.chatapp.infra.datasource.FirestoreDatasourceImpl
import com.google.firebase.firestore.QuerySnapshot
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

const val USERS = "Users"

@Singleton
class FirestoreRepositoryImpl @Inject constructor(
    private val firestoreClass: FirestoreDatasourceImpl
) : FirestoreRepository{

    override suspend fun addUser(user: UserModel){
        firestoreClass.addUser(user)
    }

    @ExperimentalCoroutinesApi
    override fun getAllUsers() : Flow<QuerySnapshot?>
//    : List<UserModel>
    {
//        val result =
         return firestoreClass.getAllUsers()
//        Log.d("TAG3", result.toString())
//        return result
    }


}