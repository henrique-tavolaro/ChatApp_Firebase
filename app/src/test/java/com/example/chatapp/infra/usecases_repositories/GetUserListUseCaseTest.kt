package com.example.chatapp.infra.usecases_repositories

import com.example.chatapp.domain.entity.UserModel
import com.example.chatapp.domain.repositories.GetUserList
import com.example.chatapp.infra.datasource.FakeFirestoreDatasource
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert
import org.junit.Before
import org.junit.Test

class GetUserListUseCaseTest {

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

    private val userList = listOf(user2)

    private lateinit var datasource: FakeFirestoreDatasource
    private lateinit var usecase: GetUserList

    @Before
    fun setup(){
        datasource = FakeFirestoreDatasource()
        usecase = GetUserListUseCase(datasource)
    }

    @ExperimentalCoroutinesApi
    @InternalCoroutinesApi
    @Test
    fun shouldReturnFlowOfUsersWithoutCurrentUser() = runBlockingTest {
        usecase.addUser(user2)
        usecase.addUser(user1)

        val users = mutableListOf<UserModel>()

        usecase.getUserList(user1.id).collect {
            if (it != null) {
                for(i in it){
                    users.add(i)
                }
            }
        }
        Assert.assertEquals(users, userList )
    }

}