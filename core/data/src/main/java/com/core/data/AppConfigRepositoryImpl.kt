package com.core.data

import com.core.data.localstorage.ConfigDatabase
import com.core.data.pref.ConfigPrefManager
import com.core.data.remote.ConfigDataSource
import javax.inject.Inject

/*
 * Created by Sudhanshu Kumar on 12/11/23.
 */

class AppConfigRepositoryImpl @Inject constructor (
    private val configDataSource: ConfigDataSource,
    private val configPrefManager: ConfigPrefManager,
    private val configDb: ConfigDatabase
): AppConfigRepository {

    override suspend fun checkMaintenance() {
        val maintenance = configDataSource.checkMaintenance()
        return if(maintenance.success) {
            configPrefManager.setMaintenance(maintenance.maintenance)
        } else {
            configPrefManager.setMaintenance(false)
        }
    }

    override suspend fun checkUpdate() {
        val update = configDataSource.checkUpdate()
        if(update.success) {
            update.data?.let {
                configPrefManager.setForceUpdate(it.forceUpdate)
                configPrefManager.setLatestVersion(it.liveVersion)
            }
        }
    }

    override suspend fun getFeatureConfig() {
        val featureConfig = configDataSource.getFeatureConfig()
        if(featureConfig.success) {
            featureConfig.featureList?.let { list ->
                configDb.dao.saveFeatureConfig(list)
            }
        }
    }

}