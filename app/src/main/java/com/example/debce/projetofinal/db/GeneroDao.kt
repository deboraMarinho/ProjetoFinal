package com.example.debce.projetofinal.db

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.*

@Dao
interface GeneroDao {
    @Insert
    fun insert(genero: Genero)

    @Update
    fun update(genero: Genero)

    @Delete
    fun delete(genero: Genero)

    @Query("DELETE FROM genero_table")
    fun deleteAll()

    @Query("SELECT * FROM genero_table")
    fun getAll(): LiveData<List<Genero>>
}