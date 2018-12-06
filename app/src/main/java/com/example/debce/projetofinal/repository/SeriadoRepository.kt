package com.example.debce.projetofinal.repository

import android.arch.lifecycle.LiveData
import com.example.debce.projetofinal.db.Seriado
import com.example.debce.projetofinal.db.SeriadoDao

class SeriadoRepository(private val seriadoDao: SeriadoDao) {

    val allSeriados: LiveData<List<Seriado>> = seriadoDao.getAll()

    fun insert(seriado: Seriado){
        seriadoDao.insert(seriado)
    }

    fun update(seriado: Seriado){
        seriadoDao.update(seriado)
    }

    fun delete(seriado: Seriado){
        seriadoDao.delete(seriado)
    }
}