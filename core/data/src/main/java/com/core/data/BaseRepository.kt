package com.core.data

import com.core.util.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

abstract class BaseRepository {

    suspend fun <T> safeApiCall(
        apiCall:(suspend () ->T)?
    ): Resource<T> {
        return withContext(Dispatchers.IO){
            apiCall?.let {
                try {
                    Resource.Success(apiCall.invoke())
                }catch (e:Exception){
                    Resource.Error(e.message)
                }
            } ?: run {
                Resource.Error("Retrofit was null")
            }
        }
    }
}