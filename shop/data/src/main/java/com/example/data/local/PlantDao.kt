package com.example.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.data.model.ResPlant

/*
 * Created by Sudhanshu Kumar on 03/01/25.
 */

@Dao
interface PlantDao {

    @Insert
    suspend fun upsertAll(plants: List<ResPlant>)

    @Query("DELETE FROM resplant")
    suspend fun clearAll()

}