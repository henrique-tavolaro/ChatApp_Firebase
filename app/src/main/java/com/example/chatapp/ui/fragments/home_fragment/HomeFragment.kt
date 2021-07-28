package com.example.chatapp.ui.fragments.home_fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.net.toUri
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.chatapp.domain.entity.UserModel
import com.example.chatapp.ui.composables.UsersListScreen
import com.example.chatapp.ui.composables.loadImageUri
import com.example.chatapp.ui.theme.ChatAppTheme
import com.example.chatapp.ui.theme.Purple200
import com.example.chatapp.ui.theme.Purple700
import com.example.chatapp.utils.DEFAULT_IMAGE
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private val viewModel: HomeViewModel by viewModels()

    val args: HomeFragmentArgs by navArgs()

    @ExperimentalMaterialApi
    @ExperimentalCoroutinesApi
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        val user: UserModel = UserModel(
            id = args.id,
            name = args.name,
            photoUrl = args.photoUrl,
            email = args.email
        )



        return ComposeView(requireContext()).apply {


            setContent {
                val userList = viewModel.userList.value

                Log.d("TAG", userList.toString())
                MaterialTheme {

                    val state = rememberScaffoldState()
                    val coroutineScope = rememberCoroutineScope()


                    ChatAppTheme() {
                        Scaffold(
                            scaffoldState = state,
                            topBar = {
                                TopAppBar(
                                    title = { Text(text = "Welcome ${user.name}!") },
                                    navigationIcon = {
                                        IconButton(onClick = {
                                            coroutineScope.launch {
                                                state.drawerState.open()
                                            }
                                        }) {
                                            Icon(Icons.Default.Menu, contentDescription = null)
                                        }
                                    }
                                )
                            },
                            drawerContent = {
                                Column {
                                    Column(
                                        modifier = Modifier
                                            .fillMaxHeight(0.32f)
                                            .fillMaxWidth()
                                            .background(
                                                brush = Brush.horizontalGradient(
                                                    colors = listOf(
                                                        Purple200,
                                                        Purple700
                                                    )
                                                )
                                            )
                                    ) {
                                        user.photoUrl.let { url ->
                                            val image =
                                                loadImageUri(
                                                    url = url.toUri(),
                                                    defaultImage = DEFAULT_IMAGE
                                                ).value
                                            image?.let { img ->
                                                Image(
                                                    bitmap = img.asImageBitmap(),
                                                    modifier = Modifier
                                                        .height(152.dp)
                                                        .width(152.dp)
                                                        .padding(top = 16.dp, start = 16.dp)
                                                        .clip(CircleShape),
                                                    contentScale = ContentScale.Crop,
                                                    contentDescription = null
                                                )
                                            }
                                        }
                                        Text(
                                            modifier = Modifier
                                                .padding(start = 24.dp, top = 8.dp),
                                            text = user.name,
                                            fontSize = 20.sp,
                                            color = MaterialTheme.colors.onPrimary
                                        )
                                        Text(
                                            modifier = Modifier
                                                .padding(start = 24.dp, bottom = 8.dp, top = 8.dp),
                                            text = user.email,
                                            fontSize = 16.sp,
                                            color = MaterialTheme.colors.onPrimary
                                        )
                                    }
                                    Divider(
                                        modifier = Modifier.fillMaxWidth(),
                                        thickness = 1.dp,
                                        color = Color.Black
                                    )
                                    Spacer(modifier = Modifier.padding(8.dp))
                                }

                            }
                        ) {
                            UsersListScreen(viewModel = viewModel, navController = findNavController())
                        }
                    }

                }
            }
        }
    }
}