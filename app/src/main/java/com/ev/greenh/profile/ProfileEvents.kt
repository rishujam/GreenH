package com.ev.greenh.profile

/*
 * Created by Sudhanshu Kumar on 29/10/24.
 */

sealed class ProfileEvents {
    object EditClick : ProfileEvents()
    object AuthClick : ProfileEvents()
    object ContactClick : ProfileEvents()
}