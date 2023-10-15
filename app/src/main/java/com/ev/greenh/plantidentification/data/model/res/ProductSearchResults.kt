package com.ev.greenh.plantidentification.data.model.res

/*
 * Created by Sudhanshu Kumar on 15/10/23.
 */

data class ProductSearchResults(
    val indexTime: String,
    val results: List<Result>,
    val productGroupedResults: List<GroupedResult>
)