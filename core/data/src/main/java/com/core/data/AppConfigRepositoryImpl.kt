package com.core.data

import com.core.data.localstorage.ConfigDatabase
import com.core.data.pref.ConfigPref
import com.core.data.remote.ConfigDataSource

/*
 * Created by Sudhanshu Kumar on 12/11/23.
 */

class AppConfigRepositoryImpl(
    private val configDataSource: ConfigDataSource,
    private val configPref: ConfigPref,
    private val configDb: ConfigDatabase
): AppConfigRepository {

    override suspend fun checkMaintenance() {
        val maintenance = configDataSource.checkMaintenance()
        return if(maintenance.success) {
            configPref.setMaintenance(maintenance.maintenance)
        } else {
            configPref.setMaintenance(false)
        }
    }

    override suspend fun checkUpdate() {
        val update = configDataSource.checkUpdate()
        if(update.success) {
            update.data?.let {
                configPref.setForceUpdate(it.forceUpdate)
                configPref.setLatestVersion(it.liveVersion)
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