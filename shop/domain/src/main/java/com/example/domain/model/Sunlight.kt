package com.example.domain.model

import java.io.Serializable

/*
 * Created by Sudhanshu Kumar on 14/11/24.
 */

enum class Sunlight(val value: String): Serializable {
    DIRECT("Direct"),
    IN_DIRECT("In Direct"),
    NO("No"),
    EMPTY("");

    companion object {
        fun fromValue(value: String): Sunlight {
            return values().find { it.value.equals(value, ignoreCase = true) }
                ?: EMPTY
        }
    }
}