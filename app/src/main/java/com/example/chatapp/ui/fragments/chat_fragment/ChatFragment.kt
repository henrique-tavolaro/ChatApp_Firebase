package com.example.chatapp.ui.fragments.chat_fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.unit.dp
import androidx.core.net.toUri
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.chatapp.domain.entity.UserModel
import com.example.chatapp.ui.composables.loadImageUri
import com.example.chatapp.ui.theme.ChatAppTheme
import com.example.chatapp.utils.DEFAULT_IMAGE


class ChatFragment : Fragment() {

    val viewModel: ChatViewModel by viewModels()

    val args: ChatFragmentArgs by navArgs()



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

        val message = viewModel.message

        return ComposeView(requireContext()).apply {
            setContent {
                ChatAppTheme {
                    Scaffold(
                        topBar = {
                            TopAppBar(
                                title = { Text(text = "${user.name}") },
                                navigationIcon = {
                                    IconButton(onClick = {
                                        findNavController().popBackStack()
                                    },
                                    ) {
                                        Icon(Icons.Default.ArrowBack, contentDescription = null)
                                    }
                                },
                                actions = {
                                    Row(
                                        modifier = Modifier.padding(end = 8.dp)
                                    ){
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
                                                        .height(44.dp)
                                                        .width(44.dp)
//                                                .padding(8.dp)
                                                        .clip(CircleShape),
                                                    contentScale = ContentScale.Crop,
                                                    contentDescription = null
                                                )
                                            }
                                        }
                                    }
                                }
                            )
                        }
                    ) {
                        Column(
                            modifier = Modifier
                                .padding(8.dp)
                                .fillMaxSize(),

                            verticalArrangement = Arrangement.Bottom

                        ) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceAround
                            ){
                                OutlinedTextField(
                                    value = message.value,
                                    onValueChange = {
                                         viewModel.onMessageChange(it)
                                    },
                                    label = {
                                        Text("Message...")
                                    }
                                )

                                Button(

                                    onClick = {

                                    }) {
                                    Text(text = "SEND")
                                }
                            }

                        }
                    }
                }
            }
        }
    }
}
