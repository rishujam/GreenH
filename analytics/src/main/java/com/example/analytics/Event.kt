package com.example.analytics

data class Event(
    val id: String,
    val name: String,
    val timeStamp: Long,
    val screenName: String,
    val userId: String
)
