package com.ev.greenh.launcher

import com.core.data.AppConfigRepository
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

/*
 * Created by Sudhanshu Kumar on 11/02/24.
 */

class GetAppStartUpRecipeUseCase @Inject constructor (
    private val appConfigRepository: AppConfigRepository
) {

    suspend operator fun invoke(): Flow<Boolean> = flow {
        coroutineScope {
            val featureDef = async {
                appConfigRepository.getAndSaveFeatureConfig()
            }
            val updateInfoDef = async {
                appConfigRepository.getAndSaveUpdate()
            }
            val maintenanceDef = async {
                appConfigRepository.getAndSaveMaintenance()
            }
            val deferredList = listOf(featureDef, updateInfoDef, maintenanceDef)
            deferredList.awaitAll()
            emit(true)
        }
    }

}