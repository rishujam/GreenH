package com.ev.greenh.home

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import com.ev.greenh.grow.ui.LocalPlantStep1Fragment
import com.ev.greenh.ui.MainActivity
import com.ev.greenh.util.findActivity

/*
 * Created by Sudhanshu Kumar on 20/10/23.
 */

@Composable
fun HomeScreen() {
    val context = LocalContext.current
    Button(
        modifier = Modifier.fillMaxWidth(),
        onClick = {
            val activity = context.findActivity()
            val fragment = LocalPlantStep1Fragment()
            (activity as? MainActivity)?.setCurrentFragmentBack(fragment)
        }
    ) {
        Text(text = "Click Me")
    }
}