package com.example.chatapp.infra.usecases_repositories

import com.example.chatapp.domain.entity.UserModel
import com.example.chatapp.domain.repositories.AddUser
import com.example.chatapp.infra.datasource.FakeFirestoreDatasource
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Before
import org.junit.Test

class AddUserUseCaseTest {

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
    private val userList = listOf(user1, user2)

    private lateinit var datasource: FakeFirestoreDatasource
    private lateinit var usecase: AddUser

    @Before
    fun setup(){
        datasource = FakeFirestoreDatasource()
        usecase = AddUserUseCase(datasource)
    }

    @Test
    fun addUser() = runBlocking {
        usecase.addUser(user1)
        usecase.addUser(user2)

        val users = usecase.getAllUsers()
        Assert.assertEquals(users, userList)
    }

}