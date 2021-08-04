package com.example.chatapp.ui.fragments.home_fragment

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.chatapp.MainCoroutineRule
import com.example.chatapp.domain.entity.UserModel
import com.example.chatapp.domain.repositories.AddUser
import com.example.chatapp.domain.repositories.GetUserList
import com.example.chatapp.infra.fake_usecases_repositories.FakeAddUserUseCase
import com.example.chatapp.infra.fake_usecases_repositories.FakeGetUserListUseCase
import com.example.chatapp.infra.usecases_repositories.AddUserUseCase
import com.example.chatapp.infra.usecases_repositories.GetUserListUseCase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class HomeViewModelTest {

    private lateinit var homeViewModel: HomeViewModel

    private lateinit var addUserUseCase: AddUser
    private lateinit var getUserListUseCase: GetUserList

    private val user1 =
        UserModel(id = "id1", name = "name1", email = "email1", photoUrl = "photoUrl1")
    private val user2 =
        UserModel(id = "id2", name = "name2", email = "email2", photoUrl = "photoUrl2")
    private val user3 =
        UserModel(id = "id3", name = "name3", email = "email3", photoUrl = "photoUrl3")

    private val userList = listOf(user1, user2, user3)
    private val userListWithoutUser = listOf(user2, user3)

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @ExperimentalCoroutinesApi
    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    @Before
    fun setup() {
        addUserUseCase = FakeAddUserUseCase()
        getUserListUseCase = FakeGetUserListUseCase()
        homeViewModel = HomeViewModel(addUserUseCase, getUserListUseCase)
    }

    @ExperimentalCoroutinesApi
    @Test
    fun shouldAddUserToRepository() = runBlockingTest {
        homeViewModel.addUser(user1)
        homeViewModel.addUser(user2)
        homeViewModel.addUser(user3)

        val list = homeViewModel.getAllUsers()

        assertEquals(userList, list)
    }

    @InternalCoroutinesApi
    @ExperimentalCoroutinesApi
    @Test
    fun shouldFlowListOfUserWithoutUser() = runBlockingTest {
        homeViewModel.addUserTest(user1)
        homeViewModel.addUserTest(user2)
        homeViewModel.addUserTest(user3)

        val list = mutableListOf<UserModel>()

        homeViewModel.getUserList(user1.id).collect {
            it?.let {
                for (i in it) {
                    list.add(i)
                }
            }
        }

        assertEquals(userListWithoutUser, list)
    }
}