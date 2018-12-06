package com.example.debce.projetofinal.db

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.*

@Dao
interface SeriadoDao {

    @Insert
    fun insert(seriado: Seriado)

    @Update
    fun update(seriado: Seriado)

    @Delete
    fun delete(seriado: Seriado)

    @Query("SELECT FROM seriado_table")
    fun deleteAll()

    @Query("SELECT * FROM seriado_table")
    fun getAll():LiveData<List<Seriado>>
}