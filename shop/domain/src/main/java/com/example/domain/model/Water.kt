package com.example.domain.model

import java.io.Serializable

/*
 * Created by Sudhanshu Kumar on 14/11/24.
 */

enum class Water(val value: String): Serializable {
    ONCE_A_DAY("Once a Day"),
    TWICE_A_DAY("Twice a Day"),
    ONCE_A_WEEK("Once a Week"),
    TWICE_A_WEEK("Twice a Week"),
    EMPTY("");

    companion object {
        fun fromValue(value: String): Water {
            return Water.values().find { it.value.equals(value, ignoreCase = false) }
                ?: EMPTY
        }
    }
}