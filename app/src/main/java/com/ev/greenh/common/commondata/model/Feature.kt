package com.ev.greenh.common.commondata.model

import com.google.firebase.firestore.PropertyName
import com.google.gson.annotations.SerializedName

/*
 * Created by Sudhanshu Kumar on 12/11/23.
 */

data class Feature(
    val id: String? = null,
    @SerializedName("is_enabled")
    @get:PropertyName("is_enabled")
    @set:PropertyName("is_enabled")
    var isEnabled: Boolean = false,
    val message: String? = null
)
