package com.example.whiteboardformatter.data

import androidx.room.*
import android.content.Context
import android.graphics.Color
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.whiteboardformatter.data.dao.TableDao
import com.example.whiteboardformatter.data.model.Text
import com.example.whiteboardformatter.data.model.WhiteboardEntity


@Database(
    entities = arrayOf(
        WhiteboardEntity::class,
        Text::class
    ), version = 1
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun dao(): TableDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }
            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "task_database"
                )
//                    .addCallback()
//                    .addMigrations()
                    .build()
                INSTANCE = instance
                return instance
            }
        }
    }
}

