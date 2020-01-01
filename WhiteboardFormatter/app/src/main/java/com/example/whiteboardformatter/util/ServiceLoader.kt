package com.example.whiteboardformatter.util

import android.content.Context
import com.example.whiteboardformatter.data.repository.Repository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job

object ServiceLoader {
    private var database : AppDatabase? = null
    var repository: Repository? = null

    fun provideRepository(context: Context):Repository{
        synchronized(this) {
            return repository
                ?: createRepository(
                    context
                )
        }
    }

    private fun createRepository(context:Context):Repository{
        val database = database
            ?: createDatabase(
                context
            )
        val result = Repository(database.textDao)
        repository = result
        return result
    }

    private fun createDatabase(context:Context):AppDatabase{
        val result = Appdatabase.getDatabase(context)
        database =result
        return result
    }
}