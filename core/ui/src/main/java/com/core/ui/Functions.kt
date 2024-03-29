package com.core.ui

import android.app.Activity
import android.content.Context
import android.content.ContextWrapper
import android.content.Intent
import android.graphics.Bitmap
import android.view.View
import java.io.ByteArrayOutputStream


fun<A :Activity> Activity.startNewActivity(activity:Class<A>){
    Intent(this,activity).also {
        it.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(it)
    }
}

fun View.hide() {
    visibility = View.GONE
}

fun View.show() {
    visibility = View.VISIBLE
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

fun Bitmap.toByteArray(): ByteArray {
    val stream = ByteArrayOutputStream()
    this.compress(Bitmap.CompressFormat.PNG, 100, stream)
    return stream.toByteArray()
//    this.recycle()
}

