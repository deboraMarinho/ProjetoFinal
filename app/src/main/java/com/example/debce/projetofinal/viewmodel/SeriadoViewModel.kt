package com.example.debce.projetofinal.viewmodel

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.LiveData
import com.example.debce.projetofinal.db.Seriado
import com.example.debce.projetofinal.db.SeriadoDatabase
import com.example.debce.projetofinal.repository.SeriadoRepository
import kotlinx.coroutines.experimental.*
import kotlinx.coroutines.experimental.android.Main
import kotlin.coroutines.experimental.CoroutineContext

class SeriadoViewModel(application: Application): AndroidViewModel(application) {

    private val repository: SeriadoRepository
    val allSeriado: LiveData<List<Seriado>>

    private var parentJob = Job()

    private val coroutineContext: CoroutineContext
    get() = parentJob + Dispatchers.Main

    private val scope = CoroutineScope(coroutineContext)

    init {
        val seriadoDao = SeriadoDatabase.getDatabase(application,scope).seriadoDao()
        repository = SeriadoRepository(seriadoDao)
        allSeriado = repository.allSeriados
    }

    fun insert(seriado: Seriado) = scope.launch(Dispatchers.IO){
        repository.insert(seriado)
    }

    fun update(seriado: Seriado) = scope.launch(Dispatchers.IO) {
        repository.update(seriado)
    }

    fun delete(seriado: Seriado) = scope.launch (Dispatchers.IO) {
        repository.delete(seriado)
    }
}