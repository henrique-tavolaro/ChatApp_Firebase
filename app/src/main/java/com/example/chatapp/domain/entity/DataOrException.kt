package com.example.chatapp.domain.entity

data class DataOrException<T, E : Exception?>(
    var data: T? = null,
    var e: E? = null
)
