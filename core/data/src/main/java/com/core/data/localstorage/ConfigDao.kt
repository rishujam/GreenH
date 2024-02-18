package com.core.data.localstorage

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.core.data.model.Feature

/*
 * Created by Sudhanshu Kumar on 11/02/24.
 */

@Dao
interface ConfigDao {

    @Query("SELECT * FROM Feature WHERE id = :featureId")
    fun getFeatureConfig(featureId: String): Feature?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun saveFeatureConfig(feature: List<Feature>)

}