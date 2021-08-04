package com.example.chatapp.modules

import com.example.chatapp.domain.repositories.AddMessage
import com.example.chatapp.domain.repositories.AddUser
import com.example.chatapp.domain.repositories.GetMessages
import com.example.chatapp.domain.repositories.GetUserList
import com.example.chatapp.infra.datasource.FirestoreDatasource
import com.example.chatapp.infra.usecases_repositories.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object UsecasesModule {

    @Singleton
    @Provides
    fun provideAddUser(
        firestore: FirestoreDatasource
    ) : AddUser {
        return AddUserUseCase(firestore)
    }

    @Singleton
    @Provides
    fun provideGetUserList(
        firestore: FirestoreDatasource
    ) : GetUserList {
        return GetUserListUseCase(firestore)
    }

    @Singleton
    @Provides
    fun provideAddMessage(
        firestore: FirestoreDatasource
    ) : AddMessage {
        return AddMessageUseCase(firestore)
    }

    @Singleton
    @Provides
    fun provideGetMessages(
        firestore: FirestoreDatasource
    ) : GetMessages {
        return GetMessagesUseCase(firestore)
    }


}