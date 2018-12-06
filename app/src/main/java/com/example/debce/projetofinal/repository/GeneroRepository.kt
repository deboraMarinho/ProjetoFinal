package com.example.debce.projetofinal.repository

import android.arch.lifecycle.LiveData
import com.example.debce.projetofinal.db.Genero
import com.example.debce.projetofinal.db.GeneroDao

class GeneroRepository(private val generoDao: GeneroDao) {
    val allGeneros: LiveData<List<Genero>> = generoDao.getAll()

    fun insert(genero: Genero){
        generoDao.insert(genero)
    }

    fun update(genero: Genero){
        generoDao.update(genero)
    }

    fun delete(genero: Genero){
        generoDao.delete(genero)
    }
}