package com.example.domain.model

import java.io.Serializable

/*
 * Created by Sudhanshu Kumar on 14/11/24.
 */

enum class Maintenance(val value: String): Serializable {
    HIGH("High"),
    LOW("Low"),
    EMPTY("");

    companion object {
        fun fromValue(value: String): Maintenance {
            return Maintenance.values().find { it.value.equals(value, ignoreCase = false) }
                ?: EMPTY
        }
    }
}