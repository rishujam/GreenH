package com.core.util

import android.content.ContentResolver
import android.net.Uri
import java.io.ByteArrayOutputStream
import java.io.InputStream
import kotlin.reflect.KClass
import kotlin.reflect.full.primaryConstructor

/*
 * Created by Sudhanshu Kumar on 19/10/24.
 */

fun <T : Any> Map<String, Any>.toObject(clazz: KClass<T>): T {
    val constructor = clazz.primaryConstructor
        ?: throw IllegalArgumentException("Class must have a primary constructor")

    val args = constructor.parameters.associateWith { param ->
        this[param.name] ?: throw IllegalArgumentException("Missing value for ${param.name}")
    }

    return constructor.callBy(args)
}

fun Uri.toByteArray(contentResolver: ContentResolver?): ByteArray? {
    return try {
        val inputStream: InputStream? = contentResolver?.openInputStream(this)
        inputStream?.use {
            val byteArrayOutputStream = ByteArrayOutputStream()
            val buffer = ByteArray(1024)
            var bytesRead: Int
            while (it.read(buffer).also { bytesRead = it } != -1) {
                byteArrayOutputStream.write(buffer, 0, bytesRead)
            }
            byteArrayOutputStream.toByteArray()
        }
    } catch (e: Exception) {
        e.printStackTrace()
        null
    }
}