package com.example.chatapp.ui.fragments.chat_fragment

import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ChatBubble
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.net.toUri
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.chatapp.R
import com.example.chatapp.domain.entity.Message
import com.example.chatapp.domain.entity.UserModel
import com.example.chatapp.ui.composables.ChatCard
import com.example.chatapp.ui.composables.loadImageUri
import com.example.chatapp.ui.theme.ChatAppTheme
import com.example.chatapp.utils.DEFAULT_IMAGE
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

@AndroidEntryPoint
class ChatFragment : Fragment() {

    val viewModel: ChatViewModel by viewModels()

    val args: ChatFragmentArgs by navArgs()


    //    @RequiresApi(Build.VERSION_CODES.O)
    @ExperimentalFoundationApi
    @SuppressLint("SimpleDateFormat")
    @RequiresApi(Build.VERSION_CODES.O)
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
                                title = { Text(text = user.name) },
                                navigationIcon = {
                                    IconButton(
                                        onClick = {
                                            findNavController().popBackStack()
                                        },
                                    ) {
                                        Icon(Icons.Default.ArrowBack, contentDescription = null)
                                    }
                                },
                                actions = {
                                    Row(
                                        modifier = Modifier.padding(end = 8.dp)
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
                        },
                        bottomBar = {
                            Row(
                                modifier = Modifier
                                    .padding(vertical = 16.dp, horizontal = 8.dp)
                                    .fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {

                                TextField(
                                    modifier = Modifier
                                        .fillMaxWidth(),
                                    value = message.value,
                                    onValueChange = {
                                        viewModel.onMessageChange(it)
                                    },
                                    shape = RoundedCornerShape(20.dp),

                                    placeholder = {
                                        Text("Message...")
                                    },
                                    leadingIcon = {
                                        Icon(Icons.Default.ChatBubble, contentDescription = null)
                                    },
                                    keyboardOptions = KeyboardOptions(imeAction = ImeAction.Send),
                                    keyboardActions = KeyboardActions(
                                        onSend = {
                                            val now = Date()
                                            val timestamp =
                                                SimpleDateFormat("yyMMddHHmmssZ").format(now)
                                            val date = SimpleDateFormat("dd.MM.yyyy").format(now)
                                            val time = SimpleDateFormat("h:mm a").format(now)

                                            val newMessage = Message(
                                                id = timestamp.toString(),
                                                user1id = args.userId,
                                                user2id = user.id,
                                                conversation = args.userId + user.id,
                                                date = date.toString(),
                                                time = time.toString(),
                                                message = message.value
                                            )

                                            viewModel.addMessage(newMessage)
                                            message.value = ""
                                        }
                                    ),
                                    colors = TextFieldDefaults.textFieldColors(
                                        backgroundColor = Color.White,
                                        focusedIndicatorColor = Color.Transparent,
                                        disabledIndicatorColor = Color.Transparent,
                                        unfocusedIndicatorColor = Color.Transparent
                                    )


                                )

                            }
                        }
                    ) {


                        val list = viewModel.getAllMessages(user.id, args.userId)
                            .collectAsState(initial = null).value

                        val state = rememberLazyListState()
                        val coroutineScope = rememberCoroutineScope()

                        coroutineScope.launch {
                            if (list != null && list.isNotEmpty()) {
                                state.scrollToItem(list.size - 1)
                            }
                        }

                        Box(
                            modifier = Modifier.padding(it)
                        ){
                            LazyColumn(
                                state = state,
                                modifier = Modifier
                                    .padding(8.dp)
                                    .fillMaxSize(),
                                horizontalAlignment = Alignment.CenterHorizontally,

                            ) {
                                if (list != null && list.isNotEmpty()) {

                                    val grouped = list.groupBy { it.date }

                                    grouped.forEach { (initial, list) ->
                                        stickyHeader {
                                            Card(
                                                modifier = Modifier.padding(4.dp),
                                                shape = RoundedCornerShape(10.dp),
                                                backgroundColor = MaterialTheme.colors.secondary
                                            ) {
                                                Text(
                                                    modifier = Modifier.padding(4.dp),
                                                    text = initial,
                                                    fontSize = 12.sp
                                                )
                                            }

                                        }
                                        items(items = list) {
                                            Row(
                                                Modifier.fillMaxWidth(),
                                                horizontalArrangement = if (it.user1id != args.userId)
                                                    Arrangement.Start else
                                                    Arrangement.End
                                            ) {
                                                if (it.user1id == args.userId) {
                                                    ChatCard(
                                                        message = it,
                                                        color = MaterialTheme.colors.secondaryVariant,
                                                        modifier = Modifier
                                                            .padding(
                                                                start = 32.dp,
                                                                end = 4.dp,
                                                                top = 4.dp
                                                            )
                                                    )
                                                } else {
                                                    ChatCard(
                                                        message = it,
                                                        color = Color.White,
                                                        Modifier.padding(
                                                            start = 4.dp,
                                                            end = 32.dp,
                                                            top = 4.dp
                                                        )
                                                    )
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }

                    }
                }
            }
        }
    }
}
