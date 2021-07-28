package com.example.chatapp.ui.composables

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.navigation.NavController
import com.example.chatapp.domain.entity.UserModel
import com.example.chatapp.ui.fragments.home_fragment.HomeViewModel

@ExperimentalMaterialApi
@Composable
fun UsersListScreen(viewModel: HomeViewModel, navController: NavController) {
    val getAllUsers = viewModel.getAllUsers().collectAsState(initial = null).value


    val list = getAllUsers?.map {
        it.toObject(UserModel::class.java)
    }

    LazyColumn() {

        if (list != null && list.isNotEmpty())
            items(list) { user ->
                ContactsLazyColumn(user = user, navController = navController)
            }
    }
}