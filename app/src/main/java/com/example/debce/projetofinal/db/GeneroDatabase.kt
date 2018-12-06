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

@Database(entities = [Genero::class], version = 1)
abstract class GeneroDatabase: RoomDatabase(){

    abstract fun generoDao(): GeneroDao
    companion object {
        @Volatile
        private var INSTANCE: GeneroDatabase? = null
        fun getDatabase(context: Context, scope: CoroutineScope):GeneroDatabase{
            return INSTANCE ?: synchronized(this){
                val instance = Room.databaseBuilder(
                        context.applicationContext,
                        GeneroDatabase::class.java,
                        "genero-database"
                )
                        .fallbackToDestructiveMigration()
                        .addCallback(GeneroDatabaseCalback(scope))
                        .build()
                    INSTANCE = instance
                    instance
            }
        }
    }

    private class GeneroDatabaseCalback(
            private val scope: CoroutineScope
    ): RoomDatabase.Callback(){
        override fun onOpen(db: SupportSQLiteDatabase) {
            super.onOpen(db)
            INSTANCE?.let { database ->
                scope.launch (Dispatchers.IO){
                    populaTabela(database.generoDao())
                }
            }
        }

        fun populaTabela(generoDao: GeneroDao){

        }
    }

}