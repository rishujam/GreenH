package com.ev.greenh.profile

/*
 * Created by Sudhanshu Kumar on 29/10/24.
 */

sealed class ProfileEvents {
    object Edit : ProfileEvents()
    object Auth : ProfileEvents()
    object Contact : ProfileEvents()
    object Logout : ProfileEvents()
    object LogoutConfirm : ProfileEvents()
    object AlertCancel : ProfileEvents()
    object DeleteAccount : ProfileEvents()
    object DeleteAccountConfirm : ProfileEvents()
}