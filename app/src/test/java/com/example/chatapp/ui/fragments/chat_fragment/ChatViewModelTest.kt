package com.example.chatapp.ui.fragments.chat_fragment

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.chatapp.MainCoroutineRule
import com.example.chatapp.domain.entity.Message
import com.example.chatapp.domain.entity.UserModel
import com.example.chatapp.domain.repositories.AddMessage
import com.example.chatapp.domain.repositories.GetMessages
import com.example.chatapp.infra.fake_usecases_repositories.FakeAddMessagesUseCase
import com.example.chatapp.infra.fake_usecases_repositories.FakeAddUserUseCase
import com.example.chatapp.infra.fake_usecases_repositories.FakeGetMessagesUseCase
import com.example.chatapp.infra.fake_usecases_repositories.FakeGetUserListUseCase
import com.example.chatapp.infra.usecases_repositories.AddMessageUseCase
import com.example.chatapp.infra.usecases_repositories.GetMessagesUseCase
import com.example.chatapp.ui.fragments.home_fragment.HomeViewModel
import junit.framework.Assert.assertEquals
import junit.framework.Assert.assertNotNull
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert.assertNotEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class ChatViewModelTest {

    private lateinit var chatViewModel: ChatViewModel

    private lateinit var addMessageUseCase: AddMessage
    private lateinit var getMessagesUseCase: GetMessages

    private val user1 =
        UserModel(id = "id1", name = "name1", email = "email1", photoUrl = "photoUrl1")
    private val user2 =
        UserModel(id = "id2", name = "name2", email = "email2", photoUrl = "photoUrl2")
    private val user3 =
        UserModel(id = "id3", name = "name3", email = "email2", photoUrl = "photoUrl3")

    private val message1 = Message(
        id = "id1", user1id = user1.id, user2id = user2.id,
        conversation = user1.id + user2.id, message = "message1",
        date = "10/07/2021", time = "10:15 PM"
    )
    private val message2 = Message(
        id = "id2", user1id = user2.id, user2id = user1.id,
        conversation = user2.id + user1.id, message = "message2",
        date = "10/07/2021", time = "10:15 PM"
    )
    private val message3 = Message(
        id = "id3", user1id = user1.id, user2id = user3.id,
        conversation = user1.id + user3.id, message = "message3",
        date = "10/07/2021", time = "10:15 PM"
    )
    private val message4 = Message(
        id = "id4", user1id = user1.id, user2id = user2.id,
        conversation = user1.id + user2.id, message = "message4",
        date = "10/07/2021", time = "10:15 PM"
    )
    private val message5 = Message(
        id = "id5", user1id = user3.id, user2id = user2.id,
        conversation = user3.id + user2.id, message = "message5",
        date = "10/07/2021", time = "10:15 PM"
    )

    private val allMessageList = listOf(message1, message2, message3, message4, message5)
    private val messageListUser1User2 = listOf(message1, message2, message4)

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @ExperimentalCoroutinesApi
    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    @Before
    fun setup() {
        addMessageUseCase = FakeAddMessagesUseCase()
        getMessagesUseCase = FakeGetMessagesUseCase()
        chatViewModel = ChatViewModel(
            addMessageUseCase, getMessagesUseCase
        )
    }

    @ExperimentalCoroutinesApi
    @Test
    fun shouldAddMessageToRepositoryAndReturnAllMessages() = runBlockingTest {
        chatViewModel.addMessage(message1)
        chatViewModel.addMessage(message2)
        chatViewModel.addMessage(message3)
        chatViewModel.addMessage(message4)
        chatViewModel.addMessage(message5)

        val list = chatViewModel.getMessages()

        assertEquals(allMessageList, list)
        assertNotEquals(messageListUser1User2, list)
    }

    @ExperimentalCoroutinesApi
    @Test
    fun shouldReturnMessagesUser1User2() = runBlockingTest {
        chatViewModel.addMessageTest(message1)
        chatViewModel.addMessageTest(message2)
        chatViewModel.addMessageTest(message3)
        chatViewModel.addMessageTest(message4)
        chatViewModel.addMessageTest(message5)

        val list = mutableListOf<Message>()

        chatViewModel.getAllMessages(user1.id, user2.id).collect {
            it?.let {
                for(i in it){
                    if(i.conversation == user1.id + user2.id || i.conversation == user2.id + user1.id){
                        list.add(i)
                    }
                }
            }

            assertEquals(messageListUser1User2, list)
            assertNotEquals(allMessageList, list)
        }
    }

}