package com.core.ui.model

/*
 * Created by Sudhanshu Kumar on 27/10/24.
 */

sealed class ButtonType {
    object PrimaryEnabled : ButtonType()
    object PrimaryDisabled : ButtonType()
    object SecondaryEnabled : ButtonType()
}