package com.ev.greenh

import com.core.data.AppConfigRepository
import com.core.util.Resource
import com.ev.greenh.models.AppStartupRecipe
import com.example.auth.data.localsource.UserDataPrefManager
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
    private val appConfigRepository: AppConfigRepository,
    private val userDataPrefManager: UserDataPrefManager
) {

    suspend operator fun invoke(): Flow<AppStartupRecipe> = flow {
        coroutineScope {
            val featureDef = async {
                appConfigRepository.getFeatureConfig()
            }
            val updateInfoDef = async {
                appConfigRepository.checkUpdate()
            }
            val maintenanceDef = async {
                appConfigRepository.checkMaintenance()
            }
            val deferredList = listOf(featureDef, updateInfoDef, maintenanceDef)
            deferredList.awaitAll()
            val isLoggedIn =  userDataPrefManager.isLoggedIn() ?: false
            emit(AppStartupRecipe(isLoggedIn = isLoggedIn))
        }
    }

}