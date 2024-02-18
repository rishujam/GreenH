package com.core.data

import com.core.data.model.ResFeatureConfig
import com.core.data.model.ResMaintenance
import com.core.data.model.ResUidGen
import com.core.data.model.ResUpdate

/*
 * Created by Sudhanshu Kumar on 11/02/24.
 */

interface AppConfigRepository {

    suspend fun checkMaintenance()

    suspend fun checkUpdate()

    suspend fun getFeatureConfig()

}