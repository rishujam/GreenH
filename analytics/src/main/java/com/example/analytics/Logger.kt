package com.example.analytics

fun interface Logger {

    fun logEvent(
        event: Event
    )

}