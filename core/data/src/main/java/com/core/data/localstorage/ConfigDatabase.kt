package com.core.data.localstorage

import androidx.room.Database
import androidx.room.RoomDatabase
import com.core.data.model.Feature

/*
 * Created by Sudhanshu Kumar on 11/02/24.
 */

@Database(
    entities = [Feature::class],
    version = 2
)
abstract class ConfigDatabase : RoomDatabase() {

    abstract val dao: ConfigDao

}