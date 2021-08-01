package com.example.chatapp.modules

import android.annotation.SuppressLint
import com.example.chatapp.infra.datasource.FirestoreDatasource
import com.example.chatapp.infra.datasource.FirestoreDatasourceImpl
import com.google.firebase.firestore.FirebaseFirestore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object FirestoreModule {

    @Provides
    @Singleton
    fun provideFirestore() : FirebaseFirestore = FirebaseFirestore.getInstance()

    @Singleton
    @Provides
    fun provideFirestoreDatasource() : FirestoreDatasource {
        return FirestoreDatasourceImpl(provideFirestore())
    }

}