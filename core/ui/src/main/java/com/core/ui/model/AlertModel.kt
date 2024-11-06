package com.core.ui.model

/*
 * Created by Sudhanshu Kumar on 15/11/23.
 */

data class AlertModel(
    val isShowing: Boolean = false,
    val title: String,
    val message: String,
    val cancelText: String? = null,
    val confirmText: String? = null,
    val type: AlertType
)
