package com.ev.greenh.profile.composable

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.core.ui.composable.GButton
import com.ev.greenh.profile.ProfileEvents

/*
 * Created by Sudhanshu Kumar on 05/11/24.
 */
 
@Composable
fun LoggedOutProfileScreen(
    onEvent: (ProfileEvents) -> Unit
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        GButton(modifier = Modifier, text = "Create or Login") {
            onEvent(ProfileEvents.AuthClick)
        }
    }
}