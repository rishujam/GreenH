package com.core.util

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