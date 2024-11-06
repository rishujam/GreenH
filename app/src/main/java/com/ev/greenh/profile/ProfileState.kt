package com.ev.greenh.profile

import com.core.ui.model.AlertModel
import com.core.ui.model.AlertType
import com.example.auth.data.model.UserProfile

/*
 * Created by Sudhanshu Kumar on 29/10/24.
 */
data class ProfileState(
    val isLoggedIn: Boolean? = null,
    val isLoading: Boolean? = null,
    val profile: UserProfile? = null,
    val errorMsg: String? = null,
    val contactExpanded: Boolean = false,
    val alert: AlertModel? = null
)