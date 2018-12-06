package com.example.debce.projetofinal.db

import android.arch.persistence.db.SupportSQLiteDatabase
import android.arch.persistence.room.Database
import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import android.content.Context
import kotlinx.coroutines.experimental.CoroutineScope
import kotlinx.coroutines.experimental.Dispatchers
import kotlinx.coroutines.experimental.IO
import kotlinx.coroutines.experimental.launch

@Database(entities = [Seriado::class], version = 1)
abstract class SeriadoDatabase: RoomDatabase() {

    abstract fun seriadoDao():SeriadoDao

    companion object {
        @Volatile
        private var INSTANCE: SeriadoDatabase? = null
        fun getDatabase(context: Context, scope: CoroutineScope):SeriadoDatabase{
            return INSTANCE?: synchronized(this){
                val instance = Room.databaseBuilder(
                        context.applicationContext,
                        SeriadoDatabase::class.java,
                        "seriado-database"
                )
                        .fallbackToDestructiveMigration()
                        .addCallback(SeriadoDatabaseCalback(scope))
                        .build()
                    INSTANCE = instance
                    instance
            }
        }
    }

    private class SeriadoDatabaseCalback(
            private val scope: CoroutineScope
    ):RoomDatabase.Callback(){

        override fun onOpen(db: SupportSQLiteDatabase){
            super.onOpen(db)
            INSTANCE?.let { database ->
                scope.launch(Dispatchers.IO){
                    populaTabela(database.seriadoDao())
                }
            }
        }

        fun populaTabela(seriadoDao: SeriadoDao){}
    }

}