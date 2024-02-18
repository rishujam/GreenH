package com.core.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.firebase.firestore.PropertyName
import com.google.gson.annotations.SerializedName
import org.jetbrains.annotations.NotNull

/*
 * Created by Sudhanshu Kumar on 12/11/23.
 */

@Entity
data class Feature(
    @PrimaryKey
    val id: String,
    @SerializedName("is_enabled")
    @get:PropertyName("is_enabled")
    @set:PropertyName("is_enabled")
    var isEnabled: Boolean? = false,
    val message: String? = null
)
