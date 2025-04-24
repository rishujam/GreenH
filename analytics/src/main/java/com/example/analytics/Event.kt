package com.example.analytics

import java.util.UUID

class Event private constructor(
    val id: String,
    val name: String,
    val timeStamp: Long,
    val screenName: String,
    val userId: String
) {

    class Builder {
        private var eventName: String? = null
        private var screenName: String? = null
        private var userId: String? = null

        fun userId(id: String) = apply {
            this.userId = id
        }

        fun screenName(name: String) = apply {
            this.screenName = name
        }

        fun eventName(name: String) = apply {
            this.eventName = name
        }

        fun build(): Event {
            return Event(
                id = System.currentTimeMillis().toString() + UUID.randomUUID(),
                name = eventName.orEmpty(),
                timeStamp = System.currentTimeMillis(),
                screenName = screenName.orEmpty(),
                userId = userId.orEmpty()
            )
        }

    }




}
