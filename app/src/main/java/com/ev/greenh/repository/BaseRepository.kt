package com.ev.greenh.repository

import com.ev.greenh.util.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

abstract class BaseRepository {

    suspend fun <T> safeApiCall(
        apiCall:suspend () ->T
    ): Resource<T> {
        return withContext(Dispatchers.IO){
            try {
                Resource.Success(apiCall.invoke())
            }catch (e:Exception){
                Resource.Error(e.message)
            }
        }
    }
}