package com.ev.greenh.util

import android.app.Activity
import android.content.Context
import android.content.ContextWrapper
import android.content.Intent
import android.view.View
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource

fun<A :Activity> Activity.startNewActivity(activity:Class<A>){
    Intent(this,activity).also {
        it.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(it)
    }
}

fun View.visible(isVisible:Boolean){
    visibility = if(isVisible) View.VISIBLE else View.GONE
}

fun View.enable(enabled:Boolean){
    isEnabled = enabled
    alpha = if(enabled) 1f else 0.5f
}

fun Context.findActivity(): Activity {
    var context = this
    while (context is ContextWrapper) {
        if (context is Activity) return context
        context = context.baseContext
    }
    throw IllegalStateException("no activity")
}

