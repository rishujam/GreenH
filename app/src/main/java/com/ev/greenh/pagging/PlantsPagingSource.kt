package com.ev.greenh.pagging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.ev.greenh.firebase.FirestoreSource
import com.ev.greenh.models.Plant

class PlantsPagingSource(
    private val source: FirestoreSource
): PagingSource<Int, Plant>(){

    override fun getRefreshKey(state: PagingState<Int, Plant>): Int? {
        return state.anchorPosition?.let {
            state.closestPageToPosition(it)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(it)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Plant> {
        return try {
            val position = params.key ?: 1
            val response = source.getAllPlantsPaging(position)
            LoadResult.Page(
                data = response.plants,
                prevKey = if(position == 1) null else position - 1,
                nextKey = position+1
            )
        }catch (e:Exception){
            LoadResult.Error(e)
        }
    }
}