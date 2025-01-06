package com.example.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.data.model.ResPlant

/*
 * Created by Sudhanshu Kumar on 03/01/25.
 */

@Database(
    entities = [ResPlant::class],
    version = 1
)
abstract class PlantDatabase : RoomDatabase() {

    abstract val dao: PlantDao

}