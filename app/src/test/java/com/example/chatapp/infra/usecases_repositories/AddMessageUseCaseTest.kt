package com.example.chatapp.infra.usecases_repositories

import com.example.chatapp.domain.entity.Message
import com.example.chatapp.domain.entity.UserModel
import com.example.chatapp.domain.repositories.AddMessage
import com.example.chatapp.domain.repositories.AddUser
import com.example.chatapp.infra.datasource.FakeFirestoreDatasource
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Before
import org.junit.Test

class AddMessageUseCaseTest {

    private val user1 = UserModel(id = "id1",name = "name1", email = "email1", photoUrl = "photoUrl1")
    private val user2 = UserModel(id = "id2",name = "name2", email = "email2", photoUrl = "photoUrl2")
    private val user3 = UserModel(id = "id3",name = "name3", email = "email2", photoUrl = "photoUrl3")

    private val message1 = Message(
        id = "id1", user1id = user1.id, user2id = user2.id,
        conversation = user1.id+user2.id, message = "message1",
        date = "10/07/2021", time = "10:15 PM"
    )
    private val message2 = Message(
        id = "id2", user1id = user2.id, user2id = user1.id,
        conversation = user2.id+user1.id, message = "message2",
        date = "10/07/2021", time = "10:15 PM"
    )
    private val message3 = Message(
        id = "id3", user1id = user1.id, user2id = user3.id,
        conversation = user1.id+user3.id, message = "message3",
        date = "10/07/2021", time = "10:15 PM"
    )
    private val message4 = Message(
        id = "id4", user1id = user1.id, user2id = user2.id,
        conversation = user1.id+user2.id, message = "message4",
        date = "10/07/2021", time = "10:15 PM"
    )
    private val message5 = Message(
        id = "id5", user1id = user3.id, user2id = user2.id,
        conversation = user3.id+user2.id, message = "message5",
        date = "10/07/2021", time = "10:15 PM"
    )

    private val allMessages = listOf<Message>(message1, message2, message3, message4, message5)
    private val messagesUser1User2 = listOf<Message>(message1, message2, message4)

    private lateinit var datasource: FakeFirestoreDatasource
    private lateinit var usecase: AddMessage

    @Before
    fun setup(){
        datasource = FakeFirestoreDatasource()
        usecase = AddMessageUseCase(datasource)
    }

    @Test
    fun shouldReturnMessagesOfUser1User2() = runBlocking {
        usecase.addMessage(message1)
        usecase.addMessage(message2)
        usecase.addMessage(message3)
        usecase.addMessage(message4)
        usecase.addMessage(message5)

        val messageList = usecase.getMessages(user1.id, user2.id)
        Assert.assertEquals(messageList, messagesUser1User2)
        Assert.assertNotEquals(messageList, allMessages)
    }

}