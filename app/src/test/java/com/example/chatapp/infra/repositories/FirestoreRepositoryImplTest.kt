package com.example.chatapp.infra.repositories

import com.example.chatapp.domain.entity.UserModel
import com.example.chatapp.domain.repositories.FirestoreRepository
import com.example.chatapp.infra.datasource.FakeFirestoreDatasource
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.runBlocking
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class FirestoreRepositoryImplTest {

    private val user1 = UserModel(
        id = "id1",
        name = "name1",
        email = "email1",
        photoUrl = "photoUrl1"
    )
    private val user2 = UserModel(
        id = "id2",
        name = "name2",
        email = "email2",
        photoUrl = "photoUrl2"
    )
    private val allUsersList = listOf(user1, user2)
    private val userListWithoutUser = listOf(user2)
    private lateinit var datasource: FakeFirestoreDatasource
    private lateinit var repository: FirestoreRepository

    @Before
    fun setup(){
        datasource = FakeFirestoreDatasource()
        repository = FirestoreRepositoryImpl(datasource)
    }

    @Test
    fun addUser() = runBlocking {
        repository.addUser(user1)
        repository.addUser(user2)

        val users = repository.getAllUsers()
        assertEquals(users, allUsersList)
    }

    @InternalCoroutinesApi
    @Test
    fun addUserToFirebase() = runBlocking {
        repository.addUser(user2)
        repository.addUser(user1)

        val users = mutableListOf<UserModel>()

        repository.getUserList(user1.id).collect {
            if (it != null) {
                for(i in it){
                    users.add(i)
                }
            }
        }
        assertEquals(users, userListWithoutUser )
    }

}