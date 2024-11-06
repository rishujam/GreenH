package com.core.ui.model

/*
 * Created by Sudhanshu Kumar on 06/11/24.
 */

sealed class AlertType {
    object LogoutConfirmation : AlertType()
    object DeleteConfirmation : AlertType()
    object Unknown : AlertType()
}