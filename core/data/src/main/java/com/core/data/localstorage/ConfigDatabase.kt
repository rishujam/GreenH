package com.core.data.localstorage

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.core.data.model.Feature

/*
 * Created by Sudhanshu Kumar on 11/02/24.
 */

@Database(
    entities = [Feature::class],
    version = 1
)
abstract class ConfigDatabase : RoomDatabase() {

    abstract val dao: ConfigDao

    companion object {
        @Volatile
        private var instance: ConfigDatabase? = null
        private val LOCK = Any()

        operator fun invoke(context: Context) = instance ?: synchronized(LOCK) {
            instance ?: createDatabase(context).also { instance = it }
        }

        private fun createDatabase(context: Context) =
            Room.databaseBuilder(
                context.applicationContext,
                ConfigDatabase::class.java,
                "config_db.db"
            ).build()
    }

}